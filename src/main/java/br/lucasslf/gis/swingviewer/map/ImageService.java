package br.lucasslf.gis.swingviewer.map;

import br.lucasslf.gis.rs.client.MapServerProxy;
import br.lucasslf.gis.swingviewer.component.MapLoadEvent;
import br.lucasslf.gis.swingviewer.component.MapLoadListener;
import br.lucasslf.gis.swingviewer.model.Configuration;
import br.lucasslf.gis.swingviewer.model.Extent;
import br.lucasslf.gis.swingviewer.model.Field;
import br.lucasslf.gis.swingviewer.model.ImageConfiguration;
import br.lucasslf.gis.swingviewer.utilities.MapUtils;
import br.morgade.desktop.async.AsyncInvoker;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe DynamicMapService
 */
public class ImageService implements DynamicMapService {

    /**
     * URL do serviço
     */
    private URL url;
    /**
     * Imagem do export
     */
    private BufferedImage exportImage;
    /**
     * transparência da imagem de exibição
     */
    private float alpha = 1f;

    /**
     * Id do mapa
     */
    private String id;
    private MapServerProxy service;
    private List<MapLoadListener> mapLoadListeners = new ArrayList<MapLoadListener>();
    private String coordinateSystemCode = "";
    private long lastCallTimeStamp;
    private boolean loading = false;
    private Log log = LogFactory.getLog(ImageService.class);
    private Extent exportExtent;
    private Double exportScale;
    private int exportDPI;
    private boolean visible = true;
    
    private ImageConfiguration imageConfig;

    public ImageService(URL url, String id, MapServerProxy service, String coordinateSystemCode) {
        this.url = url;
        this.id = id;
        this.service = service;
        this.coordinateSystemCode = coordinateSystemCode;
        loadConfig();
    }

    public ImageService(String url, String id, MapServerProxy service, String coordinateSystemCode) {
        try {
            this.url = new URL(url);
            this.id = id;
            this.service = service;
            this.coordinateSystemCode = coordinateSystemCode;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        loadConfig();
    }

    public ImageService(String url, float alpha, String id, MapServerProxy service, String coordinateSystemCode) {
        try {
            this.url = new URL(url);
            this.id = id;
            this.service = service;
            this.coordinateSystemCode = coordinateSystemCode;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        this.alpha = alpha;
        loadConfig();
    }

    public ImageService(URL url, float alpha, String id, MapServerProxy service, String coordinateSystemCode) {
        this.url = url;
        this.alpha = alpha;
        this.id = id;
        this.service = service;
        this.coordinateSystemCode = coordinateSystemCode;
        loadConfig();
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public String getCoordinateSystemCode() {
        return coordinateSystemCode;
    }

    public void setCoordinateSystemCode(String coordinateSystemCode) {
        this.coordinateSystemCode = coordinateSystemCode;
    }
    
    public List<Field> getFields(){
        return imageConfig.getFields();
    }

    
    @Override
    public Configuration getConfig() {
        return imageConfig;
    }

    @Override
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public MapServerProxy getService() {
        return service;
    }

    @Override
    public void setService(MapServerProxy service) {
        this.service = service;
    }
    
    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    

    /**
     * Retorna a imagem de exportação
     *
     * @return
     */
    @Override
    public void exportImage(Extent extent, Double scale, Graphics2D g2d) {
        if (exportExtent != null && exportImage != null) {
            if (exportExtent.equals(extent)) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getAlpha()));
                g2d.drawImage(exportImage, 0, 0, null);
            } else {
                double meters = MapUtils.getMetersPerPixel(exportScale, exportDPI);
                Double xTrans = (exportExtent.getXmin().doubleValue() - extent.getXmin().doubleValue()) / meters;
                Double yTrans = (-exportExtent.getYmax().doubleValue() + extent.getYmax().doubleValue()) / meters;

                BufferedImage aux = new BufferedImage(exportImage.getWidth(), exportImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D gAux = aux.createGraphics();
                if (!exportScale.equals(scale)) {
                    double scaleFactor = scale / exportScale;
                    gAux.setTransform(AffineTransform.getScaleInstance(scaleFactor, scaleFactor));
                    gAux.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                }

                gAux.drawImage(exportImage, xTrans.intValue(), yTrans.intValue(), null);

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getAlpha()));
                g2d.drawImage(aux, 0, 0, null);
            }
        }

    }

    @Override
    public void setExportImage(BufferedImage exportImage) {
        this.exportImage = exportImage;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addMapLoadListener(MapLoadListener mapLoadListener) {
        mapLoadListeners.add(mapLoadListener);
    }

    private void processMapLoadEvent(MapLoadEvent e) {
        for (MapLoadListener mapLoadListener : mapLoadListeners) {
            mapLoadListener.mapLoaded(e);
        }
    }

    private void processMapLoadingEvent(MapLoadEvent e) {
        for (MapLoadListener mapLoadListener : mapLoadListeners) {
            mapLoadListener.mapLoading(e);
        }
    }

    private void initLoad(long loadId) {
        log.debug("reloading " + getId() + " " + loadId);
        loading = true;
        processMapLoadingEvent(new MapLoadEvent(this, loadId));
    }

    private void finishLoad(long loadId) {
        log.debug("loaded " + getId() + " " + loadId);
        loading = false;
        processMapLoadEvent(new MapLoadEvent(this, loadId));
    }

    @Override
    public void cancelPendingLoad() {
        lastCallTimeStamp = 0;
        loading = false;
    }

    public void asyncReload(long callTimeStamp, Extent visibleExtent, double scale, int dpi, Dimension screenSize) {

        double metersPerPixel = MapUtils.getMetersPerPixel(scale, dpi);
        Point2D rightSuperiorCorner = new Point.Double(visibleExtent.getXmin().doubleValue() + screenSize.width * metersPerPixel, visibleExtent.getYmin().doubleValue() + screenSize.height * metersPerPixel);
        Image image = service.exportImage(url, visibleExtent.getXmin().doubleValue(), visibleExtent.getYmin().doubleValue(), rightSuperiorCorner.getX(), rightSuperiorCorner.getY(), screenSize, coordinateSystemCode);
        if (callTimeStamp == lastCallTimeStamp) {
            BufferedImage biAux = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = biAux.createGraphics();
            g.drawImage(image, 0, 0, null);
            exportExtent = (Extent) visibleExtent.clone();
            exportScale = scale;
            exportDPI = dpi;
            setExportImage(biAux);
            g.dispose();
            finishLoad(callTimeStamp);
        }
    }
    @Override
    public void reload(){
        reload(exportExtent, exportScale, exportDPI, new Dimension(exportImage.getWidth(), exportImage.getHeight()));
    }
    @Override
    public void reload(Extent visibleExtent, double scale, int dpi, Dimension screenSize) {


        final long callTimeStamp = Calendar.getInstance().getTimeInMillis();

        AsyncInvoker.cancelAllPendingTasks(getId());
        initLoad(callTimeStamp);
        lastCallTimeStamp = callTimeStamp;
        AsyncInvoker
                .create(this, "asyncReload", callTimeStamp, visibleExtent, scale, dpi, screenSize).schedule(getId());


    }

    private void loadConfig() {
        imageConfig = service.getImageConfiguration(this);
    }

    @Override
    public String toString() {
        return "ImageService{" + "url=" + url + '}';
    }
}

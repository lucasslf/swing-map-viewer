package br.lucasslf.gis.swingviewer.map;

import br.lucasslf.gis.rs.client.MapServerProxy;
import br.lucasslf.gis.swingviewer.component.MapLoadEvent;
import br.lucasslf.gis.swingviewer.component.MapLoadListener;
import br.lucasslf.gis.swingviewer.model.BaseMapConfiguration;
import br.lucasslf.gis.swingviewer.model.Configuration;
import br.lucasslf.gis.swingviewer.model.Extent;
import br.lucasslf.gis.swingviewer.model.Lod;
import br.lucasslf.gis.swingviewer.model.ScaleHelper;
import br.lucasslf.gis.swingviewer.model.TileFrame;
import br.lucasslf.gis.swingviewer.model.TileFrameLevel;
import br.lucasslf.gis.swingviewer.utilities.MapUtils;
import br.morgade.desktop.async.AsyncInvoker;
import br.morgade.desktop.async.processor.PostProcessor;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe BaseMapService
 */
public class BaseTiledMapService implements GenericMapService {

    /**
     * URL do serviço
     */
    private URL url;
    /**
     * Transparência da camada no mapa
     */
    private float alpha = 1f;
    /**
     * Id do serviço
     */
    private String id;
    /**
     * Proxy para acesso ao serviço de mapas
     */
    private MapServerProxy service;
    /**
     * Gerenciador das escalas
     */
    private BaseMapConfiguration baseTileMapConfig;
    private TileFrameLevel[] tileFrameLevels = null;
    private boolean loading = false;
    private Map<Long, Set<Point>> loadingTiles = Collections.synchronizedMap(new HashMap<Long, Set<Point>>());
    private List<MapLoadListener> mapLoadListeners = new ArrayList<MapLoadListener>();
    private int lastScaleIndexShown = -1;
    private Extent lastExtentShown = null;
    private Log log = LogFactory.getLog(BaseTiledMapService.class);
    private boolean visible = true;
    
    
    public BaseTiledMapService(URL url, String id, MapServerProxy service) {
        this.url = url;
        this.id = id;
        this.service = service;
        loadConfig();
    }

    public BaseTiledMapService(String url, String id, MapServerProxy service) {
        try {
            this.url = new URL(url);
            this.id = id;
            this.service = service;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        loadConfig();
    }

    public BaseTiledMapService(String url, float alpha, String id, MapServerProxy service) {
        try {
            this.url = new URL(url);
            this.id = id;
            this.service = service;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        this.alpha = alpha;
        loadConfig();
    }

    public BaseTiledMapService(URL url, float alpha, String id, MapServerProxy service) {
        this.url = url;
        this.alpha = alpha;
        this.id = id;
        this.service = service;
        loadConfig();
    }



    @Override
    public void exportImage(Extent extent, Double scale, Graphics2D g) {
        int scaleIndex = -1;
        for (TileFrameLevel thisTileFrameLevel : tileFrameLevels) {
            if (scale.equals(thisTileFrameLevel.getScale())) {
                scaleIndex = thisTileFrameLevel.getIndex();
            }

        }
        if (scaleIndex == -1) {
            throw new RuntimeException("Escala inexistente");
        }
        TileFrameLevel tileFrameLevel = tileFrameLevels[scaleIndex];

        Dimension screenSize = new Dimension(new Double(extent.getWidth() / tileFrameLevel.getResolution()).intValue(), new Double(extent.getHeight() / tileFrameLevel.getResolution()).intValue());

        boolean isTottalyLoaded = tileFrameLevel.areAllTilesLoadedForExtent(extent);

        if (lastExtentShown != null && scaleIndex != lastScaleIndexShown && !isTottalyLoaded) {
            BufferedImage biScale = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gScale = biScale.createGraphics();
            TileFrameLevel lastTileFrameLevel = tileFrameLevels[lastScaleIndexShown];
            Point[] tileBounds = lastTileFrameLevel.getFirstAndLastTileForExtent(lastExtentShown);
            Point leftInferiorTile = tileBounds[0];
            Point rightSuperiorTile = tileBounds[1];
            int firstColumn = leftInferiorTile.x;
            int lastColumn = rightSuperiorTile.x;
            int firstRow = rightSuperiorTile.y;
            int lastRow = leftInferiorTile.y;
            AffineTransform boundaryTranslation = createTileTranslationAffineTransform(lastTileFrameLevel, leftInferiorTile, rightSuperiorTile, lastExtentShown.getRightSuperiorCorner(), lastExtentShown.getLeftInferiorCorner());
            gScale.setTransform(boundaryTranslation);
            for (int i = firstColumn; i <= lastColumn; i++) {
                for (int j = firstRow; j <= lastRow; j++) {
                    TileFrame t = lastTileFrameLevel.getTileFrame(i, j);
                    if (t != null) {
                        Image tile = t.getImage();
                        if (tile != null) {
                            gScale.drawImage(tile, (i - firstColumn) * lastTileFrameLevel.getTileWidth(), (j - firstRow) * lastTileFrameLevel.getTileHeight(), null);
                        }
                    }
                }
            }

            gScale.dispose();
            Double horizontalOffset = ((lastExtentShown.getXmin().doubleValue() - extent.getXmin().doubleValue()) / tileFrameLevel.getResolution());
            Double verticalOffset = ((-lastExtentShown.getYmax().doubleValue() + extent.getYmax().doubleValue()) / tileFrameLevel.getResolution());
            AffineTransform a = createTileScaleAffineTransform(lastExtentShown, extent);

            horizontalOffset /= a.getScaleX();
            verticalOffset /= a.getScaleY();
            g.setTransform(a);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(biScale, horizontalOffset.intValue(), verticalOffset.intValue(), null);

        } else {
            lastExtentShown = (Extent) extent.clone();
            lastScaleIndexShown = scaleIndex;
        }

        Point2D leftInferiorCorner = extent.getLeftInferiorCorner();
        Point2D rightSuperiorCorner = extent.getRightSuperiorCorner();
        Point[] tileBounds = tileFrameLevel.getFirstAndLastTileForExtent(extent);
        Point leftInferiorTile = tileBounds[0];
        Point rightSuperiorTile = tileBounds[1];
        int firstColumn = leftInferiorTile.x;
        int lastColumn = rightSuperiorTile.x;
        int firstRow = rightSuperiorTile.y;
        int lastRow = leftInferiorTile.y;
        g.setTransform(createTileTranslationAffineTransform(tileFrameLevel, leftInferiorTile, rightSuperiorTile, rightSuperiorCorner, leftInferiorCorner));
        for (int i = firstColumn; i <= lastColumn; i++) {
            for (int j = firstRow; j <= lastRow; j++) {
                TileFrame t = tileFrameLevel.getTileFrame(i, j);
                if (t != null) {
                    Image tile = t.getImage();
                    if (tile != null) {
                        g.drawImage(tile, (i - firstColumn) * tileFrameLevel.getTileWidth(), (j - firstRow) * tileFrameLevel.getTileHeight(), null);
                    }
                }
            }
        }
        g.setTransform(AffineTransform.getTranslateInstance(0.0, 0.0));
    }

    @Override
    public void addMapLoadListener(MapLoadListener mapLoadListener) {
        mapLoadListeners.add(mapLoadListener);
    }

    public void loadTilesForExtent(final Extent visibleMapExtent, final int scaleIndex) {
        if (tileFrameLevels != null) {
            int level = scaleIndex;
            final TileFrameLevel tileFrameLevel = tileFrameLevels[level];
            if (tileFrameLevel.isInitialized()) {

                //calculo dos tiles necessários
                Point2D leftInferiorCorner = new Point.Double(visibleMapExtent.getXmin().doubleValue(), visibleMapExtent.getYmin().doubleValue());
                Point2D rightSuperiorCorner = new Point.Double(visibleMapExtent.getXmax().doubleValue(), visibleMapExtent.getYmax().doubleValue());
                Point leftInferiorTile = tileFrameLevel.getTilePosition(leftInferiorCorner.getX(), leftInferiorCorner.getY());
                Point rightSuperiorTile = tileFrameLevel.getTilePosition(rightSuperiorCorner.getX(), rightSuperiorCorner.getY());
                int firstColumn = leftInferiorTile.x;
                int lastColumn = rightSuperiorTile.x;
                int firstRow = rightSuperiorTile.y;
                int lastRow = leftInferiorTile.y;

                //config load
                final Long loadId = Calendar.getInstance().getTimeInMillis();
                initLoad(loadId);


                AsyncInvoker.cancelAllPendingTasks("asyncReloadTile");
                boolean requested = false;
                for (int i = firstColumn; i <= lastColumn; i++) {
                    for (int j = firstRow; j <= lastRow; j++) {
                        //Só carrega se o tile não já tiver sido carregado
                        if (tileFrameLevel.getTile(i, j) == null) {
                            //Chama o método asyncReloadTile de forma assíncrona

                            AsyncInvoker
                                    .create(this, "asyncReloadTile", tileFrameLevel, i, j, firstColumn, firstRow, loadId).processing(new PostProcessor() {
                                @Override
                                public Object postProccess(Object o) {
                                    if (loadingTiles.get(loadId) == null || loadingTiles.get(loadId).isEmpty()) {
                                        finishLoad(loadId);
                                    }
                                    return o;
                                }
                            }).schedule("asyncReloadTile");
                            requested = true;
                        }
                    }
                }
                if (!requested) {
                    finishLoad(loadId);
                }

            }
        }
    }

    public void asyncReloadTile(TileFrameLevel tileFrameLevel, int col, int row, int firstColumn, int firstRow, long loadId) {
        int level = tileFrameLevel.getIndex();
        String fileName = MapUtils.resolveTileFileName(level, col, row, id);
        Point ponto = new Point(col, row);

        Set<Point> loadingFramesForLevel = loadingTiles.get(loadId);

        if (loadingFramesForLevel == null) {
            loadingFramesForLevel = Collections.synchronizedSet(new HashSet<Point>());
            loadingTiles.put(loadId, loadingFramesForLevel);
        }

        loadingFramesForLevel.add(ponto);
        service.downloadTileImageFile(url, level, row, col, fileName);
        tileFrameLevel.createNewTile(col, row);
        loadingFramesForLevel.remove(ponto);
        processTileLoadEvent(new MapLoadEvent(this, loadId));

    }

    public ScaleHelper buildScaleHelperForService() {
        ScaleHelper scaleHelper = new ScaleHelper();
        for (TileFrameLevel lod : tileFrameLevels) {
            scaleHelper.addScale(lod.getScale());
        }
        scaleHelper.resetToMiddle();
        return scaleHelper;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    @Override
    public String getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    
    @Override
    public MapServerProxy getService() {
        return service;
    }
    @Override
    public Configuration getConfig() {
        return baseTileMapConfig;
    }
    @Override
    public void setService(MapServerProxy service) {
        this.service = service;
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
    public boolean isLoading() {
        return loading;
    }
    //</editor-fold>

    public void initLoad(Long loadId) {
        this.loading = true;
        processMapLoadingEvent(new MapLoadEvent(this, loadId));
    }

    public void finishLoad(Long loadId) {
        this.loading = false;
        processMapLoadEvent(new MapLoadEvent(this, loadId));
        for (TileFrameLevel l : tileFrameLevels) {
            l.sanitizeTiles();
        }
    }

    @Override
    public String toString() {
        return "BaseMapService{" + "url=" + url + '}';
    }

    private void processTileLoadEvent(MapLoadEvent e) {
        for (MapLoadListener mapLoadListener : mapLoadListeners) {
            mapLoadListener.tileLoaded(e);
        }
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

    private void createTileFrames() {
        tileFrameLevels = new TileFrameLevel[baseTileMapConfig.getTileInfo().getLods().size()];
        int levelCount = 0;
        for (Lod lod : baseTileMapConfig.getTileInfo().getLods()) {
            double mapWidthInMeters = baseTileMapConfig.getFullExtent().getXmax().doubleValue() - baseTileMapConfig.getFullExtent().getXmin().doubleValue();
            double mapHeightInMeters = baseTileMapConfig.getFullExtent().getYmax().doubleValue() - baseTileMapConfig.getFullExtent().getYmin().doubleValue();
            double mapWidthPx = mapWidthInMeters / lod.getResolution();
            double mapHeightPx = mapHeightInMeters / lod.getResolution();
            int columnCount = (int) Math.floor(mapWidthPx / baseTileMapConfig.getTileInfo().getCols());
            int rowCount = (int) Math.floor(mapHeightPx / baseTileMapConfig.getTileInfo().getRows());
            TileFrameLevel level = new TileFrameLevel();
            level.setColumnCount(columnCount);
            level.setRowCount(rowCount);
            level.setMapHeightInPixels(mapHeightPx);
            level.setMapWidthInPixels(mapWidthPx);
            level.setIndex(levelCount);
            level.setResolution(lod.getResolution());
            level.setScale(1 / lod.getScale());
            level.setTileHeight(baseTileMapConfig.getTileInfo().getRows());
            level.setTileWidth(baseTileMapConfig.getTileInfo().getCols());
            level.setFullExtent(baseTileMapConfig.getFullExtent());
            level.setOrigin(baseTileMapConfig.getTileInfo().getOrigin());
            level.setBaseMapServiceID(id);
            level.initializeLevel();
            tileFrameLevels[levelCount++] = level;

        }

    }

    private void loadConfig() {
        baseTileMapConfig = service.getBaseMapConfiguration(this);
        createTileFrames();
    }

    private AffineTransform createTileScaleAffineTransform(Extent oldExtent, Extent newExtent) {

        double xFactor = oldExtent.getWidth() / newExtent.getWidth();
        double yFactor = oldExtent.getHeight() / newExtent.getHeight();
        AffineTransform a = AffineTransform.getScaleInstance(xFactor, yFactor);
        return a;
    }

    /**
     * Cria o AffineTransform de translação para o extent visível do mapa
     *
     * @param tileFrameLevel Nível do frame de tiles
     * @param leftInferiorTile Tile inferior esquerdo da cobertura do extent
     * @param rightSuperiorTile Tile superior direito da cobertura do extent
     * @param rightSuperiorCorner Ponto superior direito do extent do mapa
     * @param leftInferiorCorner Ponto inferior esquerdo do extent do mapa
     * @return
     */
    private AffineTransform createTileTranslationAffineTransform(TileFrameLevel tileFrameLevel, Point leftInferiorTile, Point rightSuperiorTile, Point2D rightSuperiorCorner, Point2D leftInferiorCorner) {

        double desaoX = (leftInferiorCorner.getX() - tileFrameLevel.getOrigin().getX()) / tileFrameLevel.getResolution();
        double desaoY = (tileFrameLevel.getOrigin().getY() - rightSuperiorCorner.getY()) / tileFrameLevel.getResolution();
        double desinhoX = leftInferiorTile.getX() * tileFrameLevel.getTileWidth();
        double desinhoY = rightSuperiorTile.getY() * tileFrameLevel.getTileWidth();  //(rightSuperiorCorner.getY() - tileFrameLevel.getFullExtent().getYmin().doubleValue())/ tileFrameLevel.getResolution();
        double translateFactorX = -(desaoX - desinhoX);
        double translateFactorY = -(desaoY - desinhoY);
        AffineTransform a = AffineTransform.getTranslateInstance(translateFactorX, translateFactorY);
        return a;
    }
}

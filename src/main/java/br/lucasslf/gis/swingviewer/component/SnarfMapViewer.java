/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lucasslf.gis.swingviewer.component;

import br.lucasslf.gis.swingviewer.component.toc.tree.CheckBoxNodeRenderer;
import br.lucasslf.gis.swingviewer.map.BaseTiledMapService;
import br.lucasslf.gis.swingviewer.map.DynamicMapService;
import br.lucasslf.gis.swingviewer.model.Extent;
import br.lucasslf.gis.swingviewer.model.Geometry;
import static br.lucasslf.gis.swingviewer.model.GeometryType.POINT;
import static br.lucasslf.gis.swingviewer.model.GeometryType.POLYGON;
import static br.lucasslf.gis.swingviewer.model.GeometryType.POLYLINE;
import br.lucasslf.gis.swingviewer.model.ScaleHelper;
import br.lucasslf.gis.swingviewer.model.TemporaryLayer;
import br.lucasslf.gis.swingviewer.utilities.MapUtils;
import br.morgade.desktop.async.AsyncInvoker;
import br.morgade.desktop.async.processor.PostProcessor;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

/**
 *
 * @author cy81
 */
public class SnarfMapViewer extends JComponent implements MapLoadListener, ChangeListener {

    private BaseTiledMapService baseTiledMapService;
    private List<DynamicMapService> operationalLayers = new ArrayList<DynamicMapService>();
    private Extent visibleExtent;
    private Point2D cursorPosition = new Point2D.Double(0, 0);
    private String message = "";
    private ZoomCommandTimeoutThread zoomCommandThread;
    private Point2D lastDragPoint;
    private Point2D dragPoint;
    private boolean eagerLoad;
    private MapGestureState gestureState = MapGestureState.STAND_BY;
    private int dpi = 96;
    private ScaleHelper scaleHelper = null;
    private String coordinateSystemCode = "31984";
    private JSlider zoomSlider;
    private Log log = LogFactory.getLog(SnarfMapViewer.class);
    private BindingGroup bindingGroup;
    private List<TemporaryLayer> temporaryLayers;
    private BufferedImage pinIcon = null;

    static {
        AsyncInvoker.setExecutor(Executors.newFixedThreadPool(5), "asyncReloadTile");
    }

    public SnarfMapViewer() {
        super();
        try {
            pinIcon = ImageIO.read(CheckBoxNodeRenderer.class.getResourceAsStream("/icons/blue-pin.png"));
        } catch (IOException ex) {
            log.error(ex);

        }
        bindingGroup = new BindingGroup();

        //Carrega background
        scaleHelper = ScaleHelper.buildDefaultScaleHelper();
        double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);

        visibleExtent = new Extent(getSize().getWidth() * metersPerPixel - 4350000, -4350000, -1500000 + getSize().getHeight() * metersPerPixel, -1500000d);

        //Eventos do teclado
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);



                if (e.getKeyChar() == '+') {
                    processZoomCommand(1);
                } else if (e.getKeyChar() == '-') {
                    processZoomCommand(-1);
                }
            }
        };
        this.addKeyListener(keyAdapter);


        //Eventos do mouse
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent me) {


                if (gestureState != MapGestureState.DRAGGING) {
                    if (me.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                        //processa o comando de zoom
                        int zoomAmount = -me.getUnitsToScroll() / 3;

                        //   if(zoomSlider != null){
                        //        zoomSlider.setValue(zoomSlider.getValue() + zoomAmount);
                        //    }else{
                        processZoomCommand(zoomAmount, me.getPoint(), getCursorPosition());
                        // }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
                setCursorPosition(new Point2D.Double(visibleExtent.getXmin().doubleValue() + e.getPoint().getX() * metersPerPixel, visibleExtent.getYmin().doubleValue() + (getSize().getHeight() - e.getPoint().getY()) * metersPerPixel));
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dragPoint = e.getPoint();

                lastDragPoint = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (gestureState != MapGestureState.ZOOMING && (zoomCommandThread == null || !zoomCommandThread.isAlive())) {
                    dragPoint = e.getPoint();
                    try {
                        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    double meters = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);

                    double rawTranslateFactorX = (dragPoint.getX() - lastDragPoint.getX());
                    double rawTranslateFactorY = (dragPoint.getY() - lastDragPoint.getY());
                    double translateFactorX = rawTranslateFactorX * meters;
                    double translateFactorY = rawTranslateFactorY * meters;
                    //Faz a translação no extent visivel
                    visibleExtent.translate(-translateFactorX, translateFactorY);
                    //Recarrega os tiles antes de largar o mouse se eagerLoad estiver setado
                    if (eagerLoad) {
                        reloadBaseTiledMapBuffer();
                    }
                    //Aplica translação nos mapas dinamicos antes de recarregar
                    for (DynamicMapService dynamicMapService : operationalLayers) {
                        dynamicMapService.cancelPendingLoad();
                    }
                    repaint();
                    lastDragPoint = dragPoint;
                    gestureState = MapGestureState.DRAGGING;
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (gestureState != MapGestureState.STAND_BY) {
                    gestureState = MapGestureState.STAND_BY;
                    reCalculateVisibleExtent();
                    reloadBuffers();
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        };

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.addMouseWheelListener(listener);

        //Eventos do componente
        ComponentAdapter componentAdapter = new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reCalculateVisibleExtent();
                reloadBuffers();
                repaint();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                reCalculateVisibleExtent();
                reloadBuffers();
                repaint();
            }
        };
        this.addComponentListener(componentAdapter);
    }

    //<editor-fold defaultstate="collapsed" desc="API">
    public void reloadBuffers() {
        reloadBaseTiledMapBuffer();
        reloadDynamicMapBuffer();
    }
    //</editor-fold>

    private void reCalculateVisibleExtent(Point2D newInferiorLeftCorner) {
        double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        visibleExtent.setXmin(newInferiorLeftCorner.getX());
        visibleExtent.setYmin(newInferiorLeftCorner.getY());
        double newXMax = visibleExtent.getXmin().doubleValue() + getSize().getWidth() * metersPerPixel;
        double newYMax = visibleExtent.getYmin().doubleValue() + getSize().getHeight() * metersPerPixel;
        visibleExtent.setXmax(newXMax);
        visibleExtent.setYmax(newYMax);

    }

    private void reCalculateVisibleExtent() {
        double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        double newXMax = visibleExtent.getXmin().doubleValue() + getSize().getWidth() * metersPerPixel;
        double newYMax = visibleExtent.getYmin().doubleValue() + getSize().getHeight() * metersPerPixel;
        visibleExtent.setXmax(newXMax);
        visibleExtent.setYmax(newYMax);

    }

    private void applyNewScale(int zoomAmount) {
        Double newScale = scaleHelper.getIncrementedScale(zoomAmount);
        if (newScale != null) {
            scaleHelper.incrementScale(zoomAmount);
        } else {
            if (zoomAmount > 0) {
                scaleHelper.setMaxScale();
            } else {
                scaleHelper.resetScales();
            }
        }
        //zoomSlider.setValue(scaleHelper.getIndex());
    }

    private void processZoomCommand(int zoomAmount) {
        double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Point2D center = new Point2D.Double((visibleExtent.getXmin().doubleValue() + getSize().getWidth() * metersPerPixel / 2), (visibleExtent.getYmin().doubleValue() + getSize().getHeight() * metersPerPixel / 2));
        Point2D centerPixel = new Point2D.Double(getSize().getWidth() / 2, getSize().getHeight() / 2);
        processZoomCommand(zoomAmount, centerPixel, center);
    }

    private void doZoomCommandThreadMagic() {
        //Verifica a existencia previa de uma thread para controlar comandos de zoom cumulativos
        if (zoomCommandThread == null) {
            //cria nova thread se não existe
            zoomCommandThread = new ZoomCommandTimeoutThread();
            zoomCommandThread.start();
        } else {
            //se já existe verifica se está rodando ou não
            if (zoomCommandThread.isAlive()) {
                //estando rodando, atualiza os valores
                zoomCommandThread.updateTimeStamp();
            } else {
                //cria uma thread nova se a velha estiver morta
                zoomCommandThread = new ZoomCommandTimeoutThread();
                zoomCommandThread.start();
            }
        }
    }

    private void processZoomCommand(int zoomAmount, Point2D zoomPosition, Point2D cursorPosition) {
        if (zoomAmount != 0) {
            doZoomCommandThreadMagic();
            //Estado do mapa é "fazendo zoom"
            gestureState = MapGestureState.ZOOMING;

            applyNewScale(zoomAmount);

            reCalculateVisibleExtent(MapUtils.calculateNewCornerForZoom(cursorPosition, zoomPosition, scaleHelper.getScale(), dpi, getSize().getHeight()));
            repaint();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Tratamento de Eventos dos componentes">
    @Override
    public void mapLoaded(MapLoadEvent e) {
        repaint();
    }

    @Override
    public void configLoaded(MapLoadEvent e) {
    }

    @Override
    public void mapLoading(MapLoadEvent e) {
        //repaint();
    }

    @Override
    public void tileLoaded(MapLoadEvent e) {
        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        if (e.getSource().equals(zoomSlider)) {

            int amount = (zoomSlider.getValue() - scaleHelper.getIndex());
            processZoomCommand(amount);

        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Maps Buffers Reload">
    //DYNAMIC MAPS RELOAD
    public void asyncReloadDynamicMap(DynamicMapService dynamicMapService) {
        //só carrega se o mapa tiver tamanho
        if (getSize().width > 0 && getSize().height > 0) {
            if (gestureState != MapGestureState.DRAGGING) {

                dynamicMapService.reload((Extent) visibleExtent.clone(), scaleHelper.getScale(), dpi, (Dimension) this.getSize().clone());
            }
        }

    }

    private void reloadDynamicMapBuffer() {
        for (DynamicMapService dynamicMapService : operationalLayers) {
            AsyncInvoker.cancelAllPendingTasks(dynamicMapService.getUrl());
            //Chama o método asyncReload de forma assíncrona e chama o repaint() no final
            AsyncInvoker.create(this, "asyncReloadDynamicMap", dynamicMapService).processing(new PostProcessor() {
                @Override
                public Object postProccess(Object o) {

                    repaint();
                    return o;
                }
            }).schedule(dynamicMapService.getUrl());
        }
    }

    //TILE RELOADING
    public void asyncReloadTiledMapService() {
        if (baseTiledMapService != null) {
            //só carrega se o mapa tiver tamanho
            if (visibleExtent.getWidth() > 0 && visibleExtent.getHeight() > 0) {
                baseTiledMapService.loadTilesForExtent((Extent) visibleExtent.clone(), scaleHelper.getIndex());
            }
        }
    }

    private void reloadBaseTiledMapBuffer() {
        if (baseTiledMapService != null) {
            AsyncInvoker.cancelAllPendingTasks(baseTiledMapService.getUrl());
            //Chama o método asyncReload de forma assíncrona
            AsyncInvoker.create(this, "asyncReloadTiledMapService").schedule(baseTiledMapService.getUrl());
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Component Painting">
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Para melhorar performance da renderização
        if (gestureState == MapGestureState.DRAGGING) {
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        }

        //Desenha o base map
        paintBaseTileMap(g2d);

        paintDynamicLayers(g2d);

        paintTemporaryLayer(g2d);

        paintOverlay(g2d);

    }

    private void drawMultipoint(Graphics2D g2d, Geometry g) {
        Double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Double extentXMin = visibleExtent.getXmin().doubleValue();
        Double extentYMin = visibleExtent.getYmin().doubleValue();
        Double[][] points = g.getPoints();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        for (int i = 0; i < points.length; i++) {
            Double xPxOffset = (points[i][0] - extentXMin) / metersPerPixel;
            Double yPxOffset = (points[i][1] - extentYMin) / metersPerPixel;
            int x = xPxOffset.intValue() - (pinIcon.getWidth() / 2);
            int y = getSize().height - yPxOffset.intValue() - pinIcon.getHeight();
            g2d.drawImage(pinIcon, x, y, null);
        }
    }

    private void drawPoint(Graphics2D g2d, Geometry g) {
        Double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Double extentXMin = visibleExtent.getXmin().doubleValue();
        Double extentYMin = visibleExtent.getYmin().doubleValue();
        Double xPxOffset = (g.getX() - extentXMin) / metersPerPixel;
        Double yPxOffset = (g.getY() - extentYMin) / metersPerPixel;
        int x = xPxOffset.intValue() - (pinIcon.getWidth() / 2);
        int y = getSize().height - yPxOffset.intValue() - pinIcon.getHeight();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(pinIcon, x, y, null);


    }

    private void drawEnvelope(Graphics2D g2d, Geometry g, Color c, float alpha) {
        Double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Double extentXMin = visibleExtent.getXmin().doubleValue();
        Double extentYMin = visibleExtent.getYmin().doubleValue();
        Double xmin = g.getXmin();
        Double xmax = g.getXmax();
        Double ymin = g.getYmin();
        Double ymax = g.getYmax();

        double xminPx = (xmin - extentXMin) / metersPerPixel;
        double xmaxPx = ((xmax - extentXMin) / metersPerPixel);
        double yminPx = ((ymin - extentYMin) / metersPerPixel);
        double ymaxPx = getSize().height - (((ymax - extentYMin) / metersPerPixel));
        double widthPx = xmaxPx - xminPx;
        double heightPx = ymaxPx - yminPx;

        Rectangle2D r = new Rectangle2D.Double(xminPx, ymaxPx, widthPx, heightPx);
        g2d.setColor(c);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(r);

    }

    private void drawPolyline(Graphics2D g2d, Geometry g, Color c, float alpha) {
        Double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Double extentXMin = visibleExtent.getXmin().doubleValue();
        Double extentYMin = visibleExtent.getYmin().doubleValue();
        Double[][][] paths = g.getPaths();
        GeneralPath[] polylines = new GeneralPath[paths.length];
        for (int i = 0; i < paths.length; i++) {
            int[] xPoints = new int[paths[i].length];
            int[] yPoints = new int[paths[i].length];
            for (int j = 0; j < paths[i].length; j++) {

                Double xPxOffset = (paths[i][j][0] - extentXMin) / metersPerPixel;
                Double yPxOffset = (paths[i][j][1] - extentYMin) / metersPerPixel;
                xPoints[j] = xPxOffset.intValue();
                yPoints[j] = getSize().height - yPxOffset.intValue();
            }
            polylines[i] = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
            polylines[i].moveTo(xPoints[0], yPoints[0]);

            for (int index = 1; index < xPoints.length; index++) {
                polylines[i].lineTo(xPoints[index], yPoints[index]);
            };

        }
        g2d.setColor(c);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setStroke(new BasicStroke(1));
        for (GeneralPath p : polylines) {
            g2d.draw(p);
        }
    }

    private void drawPolygon(Graphics2D g2d, Geometry g, Color c, float alpha) {
        Double metersPerPixel = MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi);
        Double extentXMin = visibleExtent.getXmin().doubleValue();
        Double extentYMin = visibleExtent.getYmin().doubleValue();
        Double[][][] rings = g.getRings();
        Polygon[] polygons = new Polygon[rings.length];
        for (int i = 0; i < rings.length; i++) {
            int[] xPoints = new int[rings[i].length];
            int[] yPoints = new int[rings[i].length];
            for (int j = 0; j < rings[i].length; j++) {

                Double xPxOffset = (rings[i][j][0] - extentXMin) / metersPerPixel;
                Double yPxOffset = (rings[i][j][1] - extentYMin) / metersPerPixel;
                xPoints[j] = xPxOffset.intValue();
                yPoints[j] = getSize().height - yPxOffset.intValue();
            }
            polygons[i] = new Polygon(xPoints, yPoints, xPoints.length);
        }
        g2d.setColor(c);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setStroke(new BasicStroke(1));
        for (Polygon p : polygons) {
            g2d.fillPolygon(p);
            g2d.drawPolygon(p);
        }
    }

    private void paintTemporaryLayer(Graphics2D g2d) {
        if (temporaryLayers != null) {
            for (TemporaryLayer temporaryLayer : temporaryLayers) {
                if (temporaryLayer != null) {
                    for (Geometry g : temporaryLayer.getGeometries()) {
                        switch (g.getType()) {
                            case POLYGON:
                                drawPolygon(g2d, g, temporaryLayer.getColor(), temporaryLayer.getAlpha());
                                break;
                            case POLYLINE:
                                drawPolyline(g2d, g, temporaryLayer.getColor(), temporaryLayer.getAlpha());
                                break;
                            case POINT:
                                drawPoint(g2d, g);
                                break;
                            case ENVELOPE:
                                drawEnvelope(g2d, g, temporaryLayer.getColor(), temporaryLayer.getAlpha());
                                break;
                            case MULTIPOINT:
                                drawMultipoint(g2d, g);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void paintDynamicLayers(Graphics2D g2d) {
        //Desenha o mapa se este estiver carregado
        for (DynamicMapService dynamicLayer : operationalLayers) {
            if (dynamicLayer.isVisible()) {
                dynamicLayer.exportImage((Extent) visibleExtent.clone(), scaleHelper.getScale(), g2d);
            }
        }
    }

    private void paintBaseTileMap(Graphics2D g2d) {
        if (baseTiledMapService != null && baseTiledMapService.isVisible()) {
            baseTiledMapService.exportImage((Extent) visibleExtent.clone(), scaleHelper.getScale(), g2d);
        }
    }

    private void paintOverlay(Graphics2D g2d) {
        //Overlay
        String coordinates = MapUtils.getFormattedCoordinates(cursorPosition);

        //Retângulo
        g2d.setTransform(AffineTransform.getTranslateInstance(0, 0));
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        Font f = new Font("Verdanda", Font.BOLD, 12);
        int rectangleWidth = g2d.getFontMetrics().stringWidth(coordinates) + 25;
        g2d.fillRoundRect(5, (int) getSize().getHeight() - 90, rectangleWidth, 85, 10, 10);

        //Ponto de localização
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setFont(f);
        g2d.drawString(coordinates, 20, (int) getSize().getHeight() - 70);

        //Escala numérica
        g2d.drawString(MapUtils.getTextScale(scaleHelper.getScale()), 20, (int) getSize().getHeight() - 50);
        //Escala gráfica

        int distanciaEscala = MapUtils.getThousandRound(MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi) * (rectangleWidth - 32));

        String distanciaFormatada = MapUtils.getFormatedDistance(distanciaEscala);
        int graphicScaleLineWidth = (int) (distanciaEscala / MapUtils.getMetersPerPixel(scaleHelper.getScale(), dpi));

        g2d.drawString(distanciaFormatada, graphicScaleLineWidth - g2d.getFontMetrics().stringWidth(distanciaFormatada) / 2, (int) getSize().getHeight() - 30);
        g2d.drawLine(20, (int) getSize().getHeight() - 20, 20, (int) getSize().getHeight() - 10);
        g2d.drawLine(graphicScaleLineWidth + 20, (int) getSize().getHeight() - 20, graphicScaleLineWidth + 20, (int) getSize().getHeight() - 10);
        g2d.drawLine(20, (int) getSize().getHeight() - 10, graphicScaleLineWidth + 20, (int) getSize().getHeight() - 10);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public List<TemporaryLayer> getTemporaryLayers() {
        return temporaryLayers;
    }

    public void setTemporaryLayers(List<TemporaryLayer> temporaryLayers) {
        this.temporaryLayers = temporaryLayers;
    }

    public List<DynamicMapService> getOperationalLayers() {
        return operationalLayers;
    }

    public Extent getVisibleExtent() {
        return visibleExtent;
    }

    public MapGestureState getGestureState() {
        return gestureState;
    }

    public JSlider getZoomSlider() {
        return zoomSlider;
    }

    public void setZoomSlider(JSlider zoomSlider) {
        this.zoomSlider = zoomSlider;
        this.zoomSlider.setMaximum(scaleHelper.getMaxIndex());
        this.zoomSlider.setMinimum(0);
        this.zoomSlider.setPaintTicks(true);
        this.zoomSlider.setSnapToTicks(true);
        this.zoomSlider.repaint();
        //this.zoomSlider.setValue(scaleHelper.getIndex());

        Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, scaleHelper, BeanProperty.create("index"), zoomSlider, BeanProperty.create("value"));
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        this.zoomSlider.addChangeListener(this);
    }

    public void setOperationalLayers(List<DynamicMapService> baseMapServices) {
        this.operationalLayers = baseMapServices;
        for (DynamicMapService service : baseMapServices) {
            service.addMapLoadListener(this);
        }
        reCalculateVisibleExtent();
        reloadBuffers();
    }

    public boolean isEagerLoad() {
        return eagerLoad;
    }

    public void setEagerLoad(boolean eagerLoad) {
        boolean old = this.eagerLoad;
        this.eagerLoad = eagerLoad;
        firePropertyChange("eagerLoad", old, eagerLoad);
    }

    public void setCursorPosition(Point2D cursorPosition) {
        Point2D old = this.cursorPosition;
        this.cursorPosition = cursorPosition;
        firePropertyChange("cursorPosition", old, cursorPosition);
    }

    public Point2D getCursorPosition() {
        return (Point2D) cursorPosition.clone();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        firePropertyChange("message", null, null);
    }

    public String getCoordinateSystemCode() {
        return coordinateSystemCode;
    }

    public void setCoordinateSystemCode(String coordinateSystemCode) {
        this.coordinateSystemCode = coordinateSystemCode;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public BaseTiledMapService getBaseTiledMapService() {
        return baseTiledMapService;
    }

    public void setBaseTiledMapService(BaseTiledMapService baseTiledMapService) {
        this.baseTiledMapService = baseTiledMapService;
        this.scaleHelper = baseTiledMapService.buildScaleHelperForService();
        this.baseTiledMapService.addMapLoadListener(this);
        reCalculateVisibleExtent();
        reloadBaseTiledMapBuffer();


    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Whell Timeout Thread">
    class ZoomCommandTimeoutThread extends Thread {

        private long timeStamp;

        public ZoomCommandTimeoutThread() {
            this.timeStamp = Calendar.getInstance().getTimeInMillis();
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void updateTimeStamp() {
            this.timeStamp = Calendar.getInstance().getTimeInMillis();
        }

        @Override
        public void run() {
            while (true) {
                if (Calendar.getInstance().getTimeInMillis() - timeStamp > 500) {
                    reloadBuffers();
                    gestureState = MapGestureState.STAND_BY;

                    break;
                }
            }
        }
    }
//</editor-fold>
}

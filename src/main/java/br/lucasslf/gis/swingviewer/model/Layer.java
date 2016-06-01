package br.lucasslf.gis.swingviewer.model;

import static br.lucasslf.gis.swingviewer.model.RendererType.SIMPLE_RENDERER;
import static br.lucasslf.gis.swingviewer.model.SimpleLineSymbolStyle.DASH;
import static br.lucasslf.gis.swingviewer.model.SimpleLineSymbolStyle.DASH_DOT_DOT;
import static br.lucasslf.gis.swingviewer.model.SimpleLineSymbolStyle.DOT;
import static br.lucasslf.gis.swingviewer.model.SimpleLineSymbolStyle.SOLID;
import static br.lucasslf.gis.swingviewer.model.SimplerMarkerSymbolStyle.DIAMOND;
import static br.lucasslf.gis.swingviewer.model.SymbolType.PICTURE_MARKER;
import static br.lucasslf.gis.swingviewer.model.SymbolType.SIMPLE_FILL;
import static br.lucasslf.gis.swingviewer.model.SymbolType.SIMPLE_LINE;
import static br.lucasslf.gis.swingviewer.model.SymbolType.SIMPLE_MARKER;
import br.lucasslf.gis.swingviewer.utilities.MapUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Stack;
import javax.imageio.ImageIO;

/**
 * Classe Layer
 */
public class Layer {

    private int id;
    private String name;
    private int parentLayerId;
    private boolean defaultVisibility;
    private List<Integer> subLayerIds;
    private double minScale;
    private double maxScale;
    private boolean visible;
    private LayerConfiguration layerConfiguration;
    private List<Legend> legends;
    private BufferedImage imageSymbol;
    private Layer parentLayer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Layer getParentLayer() {
        return parentLayer;
    }

    public void setParentLayer(Layer parentLayer) {
        this.parentLayer = parentLayer;
    }

    public String getName() {
        return name;
    }

    public String getFullPathName() {
        Layer l = getParentLayer();
        String r = name;
        
        while(l!=null){
            r = l.getName()+" > "+r;
            l= l.getParentLayer();
        }
        
        return r;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentLayerId() {
        return parentLayerId;
    }

    public void setParentLayerId(int parentLayerId) {
        this.parentLayerId = parentLayerId;
    }

    public boolean isDefaultVisibility() {
        return defaultVisibility;
    }

    public void setDefaultVisibility(boolean defaultVisibility) {
        this.defaultVisibility = defaultVisibility;
        this.visible = defaultVisibility;


    }

    public List<Integer> getSubLayerIds() {
        return subLayerIds;
    }

    public void setSubLayerIds(List<Integer> subLayerIds) {
        this.subLayerIds = subLayerIds;
    }

    public double getMinScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LayerConfiguration getLayerConfiguration() {
        return layerConfiguration;
    }

    //<editor-fold defaultstate="collapsed" desc="Symbol Drawing - must refactor">
    private void loadImageSymbol() {
        BufferedImage bImageFromConvert = null;
        Graphics2D g2d;
        if (isLeaf() && layerConfiguration.getDrawingInfo() != null) {
            try {
                switch (layerConfiguration.getDrawingInfo().getRenderer().getType()) {
                    case SIMPLE_RENDERER:
                        SimpleRenderer simpleRenderer = (SimpleRenderer) layerConfiguration.getDrawingInfo().getRenderer();

                        switch (simpleRenderer.getSymbol().getType()) {
                            case PICTURE_MARKER:
                                InputStream in = new ByteArrayInputStream(((PictureMarkerSymbol) simpleRenderer.getSymbol()).getImageData());
                                bImageFromConvert = ImageIO.read(in);
                                break;

                            case SIMPLE_FILL:
                                SimpleFillSymbol simpleFillSymbol = (SimpleFillSymbol) simpleRenderer.getSymbol();
                                bImageFromConvert = new BufferedImage(23, 23, BufferedImage.TYPE_INT_ARGB);
                                g2d = bImageFromConvert.createGraphics();
                                g2d.setColor(MapUtils.getColorFromRGBAArray(simpleFillSymbol.getColor()));
                                g2d.fillRect(0, 0, 22, 22);
                                if (simpleFillSymbol.getOutline() != null) {
                                    g2d.setColor(MapUtils.getColorFromRGBAArray(simpleFillSymbol.getOutline().getColor()));
                                    g2d.setStroke(new BasicStroke(simpleFillSymbol.getOutline().getWidth()));
                                    g2d.drawRect(0, 0, 22, 22);
                                }

                                break;
                            case SIMPLE_LINE:
                                SimpleLineSymbol simpleLineSymbol = (SimpleLineSymbol) simpleRenderer.getSymbol();
                                bImageFromConvert = new BufferedImage(23, 23, BufferedImage.TYPE_INT_ARGB);
                                g2d = bImageFromConvert.createGraphics();
                                switch (simpleLineSymbol.getStyle()) {
                                    case SOLID:
                                        g2d.setColor(MapUtils.getColorFromRGBAArray(simpleLineSymbol.getColor()));
                                        g2d.setStroke(new BasicStroke(simpleLineSymbol.getWidth()));
                                        g2d.drawLine(1, 12, 21, 12);
                                        break;
                                    case DASH:
                                        g2d.setColor(MapUtils.getColorFromRGBAArray(simpleLineSymbol.getColor()));
                                        g2d.setStroke(new BasicStroke(simpleLineSymbol.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{4.0f}, 0.0f));
                                        g2d.drawLine(1, 12, 21, 12);
                                        break;
                                    case DASH_DOT:
                                        g2d.setColor(MapUtils.getColorFromRGBAArray(simpleLineSymbol.getColor()));
                                        g2d.setStroke(new BasicStroke(simpleLineSymbol.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{4.0f, 2.0f}, 0.0f));
                                        g2d.drawLine(1, 12, 21, 12);
                                        break;
                                    case DASH_DOT_DOT:
                                        g2d.setColor(MapUtils.getColorFromRGBAArray(simpleLineSymbol.getColor()));
                                        g2d.setStroke(new BasicStroke(simpleLineSymbol.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2.0f, 4.0f, 2.0f}, 0.0f));
                                        g2d.drawLine(1, 12, 21, 12);
                                        break;
                                    case DOT:
                                        g2d.setColor(MapUtils.getColorFromRGBAArray(simpleLineSymbol.getColor()));
                                        g2d.setStroke(new BasicStroke(simpleLineSymbol.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2.0f}, 0.0f));
                                        g2d.drawLine(1, 12, 21, 12);
                                        break;
                                }
                                break;

                            case SIMPLE_MARKER:
                                SimpleMarkerSymbol simpleMarkerSymbol = (SimpleMarkerSymbol) simpleRenderer.getSymbol();
                                switch (simpleMarkerSymbol.getStyle()) {
                                    case DIAMOND:
                                        bImageFromConvert = new BufferedImage(23, 23, BufferedImage.TYPE_INT_ARGB);
                                        g2d = bImageFromConvert.createGraphics();
                                        Color fillColor = MapUtils.getColorFromRGBAArray(simpleMarkerSymbol.getColor());

                                        drawFilledDiamond(g2d, 1, 1, 21, 21, fillColor, simpleMarkerSymbol.getOutline());
                                        break;
                                    case SQUARE:
                                        bImageFromConvert = new BufferedImage(23, 23, BufferedImage.TYPE_INT_ARGB);
                                        g2d = bImageFromConvert.createGraphics();
                                        fillColor = MapUtils.getColorFromRGBAArray(simpleMarkerSymbol.getColor());

                                        drawFilledSquare(g2d, 1, 1, 21, 21, fillColor, simpleMarkerSymbol.getOutline());
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        imageSymbol = bImageFromConvert;
    }

    private void drawFilledSquare(Graphics2D g, int x1, int y1, int x2, int y2, Color color, Outline outline) {
        Color outlineColor = color;
        float outlineWidth = 1;
        if (outline != null) {
            outlineColor = MapUtils.getColorFromRGBAArray(outline.getColor());
            outlineWidth = outline.getWidth();
        }
        Polygon p = new Polygon();

        p.addPoint(x1, y1);
        p.addPoint(x2, y1);
        p.addPoint(x2, y2);
        p.addPoint(x1, y1);
        g.setColor(color);
        g.fillPolygon(p);
        g.setStroke(new BasicStroke(outlineWidth));
        g.setColor(outlineColor);
        g.drawPolygon(p);
    }

    private void drawFilledDiamond(Graphics2D g, int x1, int y1, int x2, int y2, Color color, Outline outline) {
        Color outlineColor = color;
        float outlineWidth = 1;
        if (outline != null) {
            outlineColor = MapUtils.getColorFromRGBAArray(outline.getColor());
            outlineWidth = outline.getWidth();
        }
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        Polygon p = new Polygon();

        p.addPoint(x1, y);
        p.addPoint(x, y1);
        p.addPoint(x2, y);
        p.addPoint(x, y2);
        g.setColor(color);
        g.fillPolygon(p);
        g.setStroke(new BasicStroke(outlineWidth));
        g.setColor(outlineColor);
        g.drawPolygon(p);
    }
    //</editor-fold>

    public void setLayerConfiguration(LayerConfiguration layerConfiguration) {
        this.layerConfiguration = layerConfiguration;
        loadImageSymbol();


    }

    public boolean isLeaf() {
        return (subLayerIds == null) || (subLayerIds.isEmpty());
    }

    //TODO: Outros tipos de simbologia
    public BufferedImage getImageSymbol() {

        return imageSymbol;

    }

    public List<Legend> getLegends() {
        return legends;
    }

    public void setLegends(List<Legend> legends) {
        this.legends = legends;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Layer other = (Layer) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[Id:{" + id + "} name:{" + name + "}]";
    }
}

package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class SimpleMarkerSymbol extends Symbol{
    private SimplerMarkerSymbolStyle style;
    private int[] color;
    private int size;
    private int angle;
    private int xoffset;
    private int yoffset;
    private Outline outline;

    public SimplerMarkerSymbolStyle getStyle() {
        return style;
    }

    public void setStyle(SimplerMarkerSymbolStyle style) {
        this.style = style;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getXoffset() {
        return xoffset;
    }

    public void setXoffset(int xoffset) {
        this.xoffset = xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
    }

    public Outline getOutline() {
        return outline;
    }

    public void setOutline(Outline outline) {
        this.outline = outline;
    }
    
    
}

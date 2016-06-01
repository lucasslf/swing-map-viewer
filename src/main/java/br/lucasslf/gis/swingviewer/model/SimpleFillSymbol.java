package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class SimpleFillSymbol extends Symbol {
    private String style;
    private int[] color;
    private Outline outline;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public Outline getOutline() {
        return outline;
    }

    public void setOutline(Outline outline) {
        this.outline = outline;
    }
    
    
}

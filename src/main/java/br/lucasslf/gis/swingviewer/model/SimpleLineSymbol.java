package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class SimpleLineSymbol extends Symbol{
    private SimpleLineSymbolStyle style;
    private int[] color;
    private float width;

    public SimpleLineSymbolStyle getStyle() {
        return style;
    }

    public void setStyle(SimpleLineSymbolStyle style) {
        this.style = style;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
    
}

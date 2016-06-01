package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class TextSymbol extends Symbol {

    private int[] color;
    private int[] backgroundColor;
    private int[] borderLineColor;
    private int[] haloColor;
    private String verticalAlignment;
    private String horizontalAlignment;
    private boolean rightToLeft;
    private int angle;
    private int xoffset;
    private int yoffset;
    private boolean kerning;
    private Font font;
    private int borderLineSize;
    private int haloSize;

    public int getHaloSize() {
        return haloSize;
    }

    public void setHaloSize(int haloSize) {
        this.haloSize = haloSize;
    }

    public int[] getHaloColor() {
        return haloColor;
    }

    public void setHaloColor(int[] haloColor) {
        this.haloColor = haloColor;
    }

    public int getBorderLineSize() {
        return borderLineSize;
    }

    public void setBorderLineSize(int borderLineSize) {
        this.borderLineSize = borderLineSize;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int[] getBorderLineColor() {
        return borderLineColor;
    }

    public void setBorderLineColor(int[] borderLineColor) {
        this.borderLineColor = borderLineColor;
    }

    public String getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(String verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public String getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public boolean isRightToLeft() {
        return rightToLeft;
    }

    public void setRightToLeft(boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
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

    public boolean isKerning() {
        return kerning;
    }

    public void setKerning(boolean kerning) {
        this.kerning = kerning;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}

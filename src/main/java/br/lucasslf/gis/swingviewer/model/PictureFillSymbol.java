package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class PictureFillSymbol extends Symbol {
    private String url;
    private byte[] imageData;
    private String contentType;
    private int[] color;
    private Outline outline;
    private int width;
    private int height;
    private int angle;
    private int xoffset;
    private int yoffset;
    private int xscale;
    private int yscale;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getXscale() {
        return xscale;
    }

    public void setXscale(int xscale) {
        this.xscale = xscale;
    }

    public int getYscale() {
        return yscale;
    }

    public void setYscale(int yscale) {
        this.yscale = yscale;
    }
    
    
}

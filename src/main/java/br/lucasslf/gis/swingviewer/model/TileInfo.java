package br.lucasslf.gis.swingviewer.model;

import java.awt.Point;
import java.util.List;

/**
 * Classe TileInfo
 */
public class TileInfo {
    
    private int rows;
    private int cols;
    private int dpi;
    private String format;
    private int compressionQuality;
    private Point origin;
    private SpatialReference spatialReference;
    private List<Lod> lods;

    

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCompressionQuality() {
        return compressionQuality;
    }

    public void setCompressionQuality(int compressionQuality) {
        this.compressionQuality = compressionQuality;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public List<Lod> getLods() {
        return lods;
    }

    public void setLods(List<Lod> lods) {
        this.lods = lods;
    }

    
    
}

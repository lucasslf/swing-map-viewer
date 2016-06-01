package br.lucasslf.gis.swingviewer.model;

import java.awt.geom.Point2D;

public class Extent implements Cloneable {

    private SpatialReference spatialReference;
    private Number xmax;
    private Number xmin;
    private Number ymax;
    private Number ymin;

    public Extent() {
    }

    public Extent(Number xmax, Number xmin, Number ymax, Number ymin) {
        this.xmax = xmax;
        this.xmin = xmin;
        this.ymax = ymax;
        this.ymin = ymin;
    }

    public Extent(SpatialReference spatialReference, Number xmax, Number xmin, Number ymax, Number ymin) {
        this.spatialReference = spatialReference;
        this.xmax = xmax;
        this.xmin = xmin;
        this.ymax = ymax;
        this.ymin = ymin;
    }

    public SpatialReference getSpatialReference() {
        return this.spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public Number getXmax() {
        return this.xmax;
    }

    public void setXmax(Number xmax) {
        this.xmax = xmax;
    }

    public Number getXmin() {
        return this.xmin;
    }

    public void setXmin(Number xmin) {
        this.xmin = xmin;
    }

    public Number getYmax() {
        return this.ymax;
    }

    public void setYmax(Number ymax) {
        this.ymax = ymax;
    }

    public Number getYmin() {
        return this.ymin;
    }

    public void setYmin(Number ymin) {
        this.ymin = ymin;
    }

    public void translate(double x, double y) {

        ymax = ymax.doubleValue() + y;
        ymin = ymin.doubleValue() + y;
        xmax = xmax.doubleValue() + x;
        xmin = xmin.doubleValue() + x;

    }

    public double getHeight() {
        return ymax.doubleValue() - ymin.doubleValue();
    }

    public double getWidth() {
        return xmax.doubleValue() - xmin.doubleValue();
    }

    public Point2D getLeftInferiorCorner() {
        return new Point2D.Double(xmin.doubleValue(), ymin.doubleValue());
    }

    public Point2D getRightSuperiorCorner() {
        return new Point2D.Double(xmax.doubleValue(), ymax.doubleValue());
    }

    public Point2D getLeftSuperiorCorner(){
            return new Point2D.Double(xmin.doubleValue(), ymax.doubleValue());
    }
    
    public Point2D getRightInferiorCorner(){
            return new Point2D.Double(xmax.doubleValue(), ymin.doubleValue());
    }
    
    
    public void setLeftInferiorCorner(Point2D leftInferiorCorner) {
        xmin = leftInferiorCorner.getX();
        ymin = leftInferiorCorner.getY();
    }

    @Override
    public Object clone() {
        return new Extent(spatialReference, xmax, xmin, ymax, ymin);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.xmax != null ? this.xmax.hashCode() : 0);
        hash = 83 * hash + (this.xmin != null ? this.xmin.hashCode() : 0);
        hash = 83 * hash + (this.ymax != null ? this.ymax.hashCode() : 0);
        hash = 83 * hash + (this.ymin != null ? this.ymin.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Extent{" + "xmax=" + xmax + ", xmin=" + xmin + ", ymax=" + ymax + ", ymin=" + ymin + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Extent other = (Extent) obj;
        if (this.spatialReference != other.spatialReference && (this.spatialReference == null || !this.spatialReference.equals(other.spatialReference))) {
            return false;
        }
        if (this.xmax != other.xmax && (this.xmax == null || !this.xmax.equals(other.xmax))) {
            return false;
        }
        if (this.xmin != other.xmin && (this.xmin == null || !this.xmin.equals(other.xmin))) {
            return false;
        }
        if (this.ymax != other.ymax && (this.ymax == null || !this.ymax.equals(other.ymax))) {
            return false;
        }
        if (this.ymin != other.ymin && (this.ymin == null || !this.ymin.equals(other.ymin))) {
            return false;
        }
        return true;
    }
}

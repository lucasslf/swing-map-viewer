package br.lucasslf.gis.swingviewer.model;



/**
 *
 */
public class Geometry {

    //Common
    private SpatialReference spatialReference;
    private boolean hasZ;
    private boolean hasM;
    // Polígono
    private Double[][][] rings;
    // Point
    private Double x;
    private Double y;
    private Double z;
    //Multipoint
    private Double[][] points;
    //Polyline
    private Double[][][] paths;
    //Envelope
    private Double xmin;
    private Double ymin;
    private Double mmin;
    private Double zmin;
    private Double xmax;
    private Double ymax;
    private Double mmax;
    private Double zmax;

    
    
    public GeometryType getType(){
    
        if(rings != null)
            return GeometryType.POLYGON;
        if(xmin!=null && xmax!=null && ymin!=null && ymax!=null   )
            return GeometryType.ENVELOPE;
        if(points!=null)
            return GeometryType.MULTIPOINT;
        if(x != null && y != null)
            return GeometryType.POINT;
        if(paths!=null)
            return GeometryType.POLYLINE;
        
        return null;
        
    }
    
    public boolean isHasZ() {
        return hasZ;
    }

    public void setHasZ(boolean hasZ) {
        this.hasZ = hasZ;
    }

    public boolean isHasM() {
        return hasM;
    }

    public void setHasM(boolean hasM) {
        this.hasM = hasM;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public Double[][][] getRings() {
        return rings;
    }

    public void setRings(Double[][][] rings) {
        this.rings = rings;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double[][] getPoints() {
        return points;
    }

    public void setPoints(Double[][] points) {
        this.points = points;
    }

    public Double[][][] getPaths() {
        return paths;
    }

    public void setPaths(Double[][][] paths) {
        this.paths = paths;
    }

    public Double getXmin() {
        return xmin;
    }

    public void setXmin(Double xmin) {
        this.xmin = xmin;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
        this.ymin = ymin;
    }

    public Double getMmin() {
        return mmin;
    }

    public void setMmin(Double mmin) {
        this.mmin = mmin;
    }

    public Double getZmin() {
        return zmin;
    }

    public void setZmin(Double zmin) {
        this.zmin = zmin;
    }

    public Double getXmax() {
        return xmax;
    }

    public void setXmax(Double xmax) {
        this.xmax = xmax;
    }

    public Double getYmax() {
        return ymax;
    }

    public void setYmax(Double ymax) {
        this.ymax = ymax;
    }

    public Double getMmax() {
        return mmax;
    }

    public void setMmax(Double mmax) {
        this.mmax = mmax;
    }

    public Double getZmax() {
        return zmax;
    }

    public void setZmax(Double zmax) {
        this.zmax = zmax;
    }
    
    
}

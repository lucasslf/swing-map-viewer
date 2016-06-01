package br.lucasslf.gis.swingviewer.model;

public class SpatialReference {

    private Number wkid;
    private Number latestWkid;
    private Number vcsWkid;
    private Number latestVcsWkid;
    private String wkt;
    private String cs;

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }
    
    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
    
    public Number getWkid() {
        return this.wkid;
    }

    public void setWkid(Number wkid) {
        this.wkid = wkid;
    }

    public Number getLatestWkid() {
        return latestWkid;
    }

    public void setLatestWkid(Number latestWkid) {
        this.latestWkid = latestWkid;
    }

    public Number getVcsWkid() {
        return vcsWkid;
    }

    public void setVcsWkid(Number vcsWkid) {
        this.vcsWkid = vcsWkid;
    }

    public Number getLatestVcsWkid() {
        return latestVcsWkid;
    }

    public void setLatestVcsWkid(Number latestVcsWkid) {
        this.latestVcsWkid = latestVcsWkid;
    }
    
    
}

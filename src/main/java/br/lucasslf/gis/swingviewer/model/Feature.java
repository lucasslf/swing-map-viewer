package br.lucasslf.gis.swingviewer.model;

import java.util.Map;

/**
 *
 */
public class Feature {

    private Map<String,String> attributes;
    private Geometry geometry;

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    
    
}

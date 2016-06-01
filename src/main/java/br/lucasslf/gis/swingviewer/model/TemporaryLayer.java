package br.lucasslf.gis.swingviewer.model;

import java.awt.Color;
import java.util.List;

/**
 *
 */
public class TemporaryLayer {

    
    private List<Geometry> geometries;
    private Color color;
    private float alpha;

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    
    
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    
    
    
    public List<Geometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }
    
    
}

package br.lucasslf.gis.swingviewer.model;

import java.util.List;

/**
 *
 */
public class LayerLegend {

    private int layerId;
    private String layerName;
    private String layerType;
    private double minScale;
    private double maxScale;
    private List<Legend> legend;

    public int getLayerId() {
        return layerId;
    }

    public void setLayerId(int layerId) {
        this.layerId = layerId;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerType() {
        return layerType;
    }

    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }

    public double getMinScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public List<Legend> getLegend() {
        return legend;
    }

    public void setLegend(List<Legend> legend) {
        this.legend = legend;
    }
    
    
    
}

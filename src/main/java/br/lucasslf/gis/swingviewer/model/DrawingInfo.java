package br.lucasslf.gis.swingviewer.model;

import java.util.List;

/**
 *
 */
public class DrawingInfo {
    private Renderer renderer;
    private int transparency;
    private List<LabelClass> labelingInfo;

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public List<LabelClass> getLabelingInfo() {
        return labelingInfo;
    }

    public void setLabelingInfo(List<LabelClass> labelingInfo) {
        this.labelingInfo = labelingInfo;
    }
    
    
}

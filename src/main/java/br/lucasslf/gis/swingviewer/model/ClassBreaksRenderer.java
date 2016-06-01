package br.lucasslf.gis.swingviewer.model;

import java.util.List;

/**
 *
 */
public class ClassBreaksRenderer extends Renderer{

    private String field;
    private Double minValue;
    private List<ClassBreakInfo> classBreakInfos;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public List<ClassBreakInfo> getClassBreakInfos() {
        return classBreakInfos;
    }

    public void setClassBreakInfos(List<ClassBreakInfo> classBreakInfos) {
        this.classBreakInfos = classBreakInfos;
    }
    
    
}

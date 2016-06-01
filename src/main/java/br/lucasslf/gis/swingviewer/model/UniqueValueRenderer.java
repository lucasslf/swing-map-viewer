package br.lucasslf.gis.swingviewer.model;

import java.util.List;

/**
 *
 */
public class UniqueValueRenderer extends Renderer {
    private String field1;
    private String field2;
    private String field3;
    private String fieldDelimiter;
    private Symbol defaultSymbol;
    private String defaultLabel;
    private List<UniqueValueInfo> uniqueValueInfos;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }

    public Symbol getDefaultSymbol() {
        return defaultSymbol;
    }

    public void setDefaultSymbol(Symbol defaultSymbol) {
        this.defaultSymbol = defaultSymbol;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    public List<UniqueValueInfo> getUniqueValueInfos() {
        return uniqueValueInfos;
    }

    public void setUniqueValueInfos(List<UniqueValueInfo> uniqueValueInfos) {
        this.uniqueValueInfos = uniqueValueInfos;
    }
    
    
    
}

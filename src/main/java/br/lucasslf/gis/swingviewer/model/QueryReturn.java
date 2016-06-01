package br.lucasslf.gis.swingviewer.model;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class QueryReturn {

    private String displayFieldName;
    private List<Field> fields;
    private SpatialReference spatialReference;
    private GeometryType geometryType;
    private Map<String,String> fieldAliases;
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    
    public String getDisplayFieldName() {
        return displayFieldName;
    }

    public void setDisplayFieldName(String displayFieldName) {
        this.displayFieldName = displayFieldName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public GeometryType getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(GeometryType geometryType) {
        this.geometryType = geometryType;
    }

    public Map<String, String> getFieldAliases() {
        return fieldAliases;
    }

    public void setFieldAliases(Map<String, String> fieldAliases) {
        this.fieldAliases = fieldAliases;
    }
    
    
    
    
}

package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum GeometryType {
 POINT("esriGeometryPoint"), MULTIPOINT("esriGeometryMultipoint" ),POLYLINE("esriGeometryPolyline"),POLYGON("esriGeometryPolygon"),ENVELOPE("esriGeometryEnvelope");
    private final String value;

    private GeometryType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static GeometryType forValue(String v) {
        for (GeometryType r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

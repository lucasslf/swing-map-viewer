package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum DomainType {
 RANGE("range"), CODED_VALUE("codedValue" ),INHERITED("inherited");
    private final String value;

    private DomainType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static DomainType forValue(String v) {
        for (DomainType r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

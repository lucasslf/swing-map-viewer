package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum RendererType {

    SIMPLE_RENDERER("simple"), UNIQUE_VALUE_RENDERER("uniqueValue" ),CLASS_BREAKS("classBreaks");
    private final String value;

    private RendererType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static RendererType forValue(String v) {
        for (RendererType r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

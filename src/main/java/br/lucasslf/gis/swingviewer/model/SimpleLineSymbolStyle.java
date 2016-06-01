package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum SimpleLineSymbolStyle {
DASH("esriSLSDash"), DASH_DOT_DOT("esriSLSDashDotDot" ),DASH_DOT("esriSLSDashDot" ),DOT("esriSLSDot"),
NULL("esriSLSNull"),SOLID("esriSLSSolid");
    private final String value;

    private SimpleLineSymbolStyle(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static SimpleLineSymbolStyle forValue(String v) {
        for (SimpleLineSymbolStyle r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

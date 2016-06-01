package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum SymbolType {
SIMPLE_MARKER("esriSMS"), SIMPLE_LINE("esriSLS" ),SIMPLE_FILL("esriSFS"),
PICTURE_MARKER("esriPMS"),PICTURE_FILL("esriPFS"),TEXT("esriTS");
    private final String value;

    private SymbolType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static SymbolType forValue(String v) {
        for (SymbolType r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

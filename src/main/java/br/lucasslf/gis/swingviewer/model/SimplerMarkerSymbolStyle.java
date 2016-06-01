package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum SimplerMarkerSymbolStyle {

    CIRCLE("esriSMSCircle"), CROSS("esriSMSCross"), DIAMOND("esriSMSDiamond"),
    SQUARE("esriSMSSquare"), SMSX("esriSMSX"), TRIANGLE("esriSMSTriangle");
    
    private final String value;

    private SimplerMarkerSymbolStyle(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonValue
    public static SimplerMarkerSymbolStyle forValue(String v) {
        for (SimplerMarkerSymbolStyle r : values()) {
            if (r.value().equals(v)) {
                return r;
            }
        }
        return null;
    }
}

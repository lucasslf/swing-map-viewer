package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SimpleMarkerSymbol.class, name = "esriSMS"),
    @JsonSubTypes.Type(value = SimpleLineSymbol.class, name = "esriSLS"),
    @JsonSubTypes.Type(value = SimpleFillSymbol.class, name = "esriSFS"),
    @JsonSubTypes.Type(value = PictureMarkerSymbol.class, name = "esriPMS"),
    @JsonSubTypes.Type(value = PictureFillSymbol.class, name = "esriPFS"),
    @JsonSubTypes.Type(value = TextSymbol.class, name = "esriTS")
})
public abstract class Symbol {

    private SymbolType type;

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }
}

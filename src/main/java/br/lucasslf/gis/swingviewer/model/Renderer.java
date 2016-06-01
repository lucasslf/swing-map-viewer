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
    @JsonSubTypes.Type(value = UniqueValueRenderer.class, name = "uniqueValue"), 
    @JsonSubTypes.Type(value = ClassBreaksRenderer.class, name = "classBreaks"), 
    @JsonSubTypes.Type(value = SimpleRenderer.class, name = "simple") 
    })  
public abstract class Renderer {

    private RendererType type;

    
    public RendererType getType() {
        return type;
    }

    public void setType(RendererType type) {
        this.type = type;
    }

}

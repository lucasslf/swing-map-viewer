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
    @JsonSubTypes.Type(value = RangeDomain.class, name = "range"),
    @JsonSubTypes.Type(value = CodedValueDomain.class, name = "codedValue"),
    @JsonSubTypes.Type(value = InheritedDomain.class, name = "inherited")})
public abstract class Domain {

    private DomainType type;
    private String name;
    
    

    public DomainType getType() {
        return type;
    }

    public void setType(DomainType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

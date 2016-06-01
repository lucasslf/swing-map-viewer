package br.lucasslf.gis.swingviewer.model;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class Type {

    private int id;
    private String name;
    private Map<String,Domain> domains;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,Domain> getDomains() {
        return domains;
    }

    public void setDomains(Map<String,Domain> domains) {
        this.domains = domains;
    }
     
    
}

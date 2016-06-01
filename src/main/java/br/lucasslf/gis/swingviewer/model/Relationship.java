package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class Relationship {

    private int id;
    private String name;
    private int relatedTableId;

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

    public int getRelatedTableId() {
        return relatedTableId;
    }

    public void setRelatedTableId(int relatedTableId) {
        this.relatedTableId = relatedTableId;
    }
    
    
}

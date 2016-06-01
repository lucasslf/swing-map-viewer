package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class SimpleRenderer extends Renderer {

    private Symbol symbol;
    private String label;
    private String description;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

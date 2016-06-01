package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class ClassBreakInfo {

    private int classMaxValue;
    private String label;
    private String description;
    private Symbol symbol;

    public int getClassMaxValue() {
        return classMaxValue;
    }

    public void setClassMaxValue(int classMaxValue) {
        this.classMaxValue = classMaxValue;
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

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
    
    
}

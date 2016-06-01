package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class LabelClass {

    private String labelPlacement;
    private String labelExpression;
    private boolean useCodedValues;
    private Symbol symbol;
    private int minScale;
    private int maxScale;
    private String where;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getLabelPlacement() {
        return labelPlacement;
    }

    public void setLabelPlacement(String labelPlacement) {
        this.labelPlacement = labelPlacement;
    }

    public String getLabelExpression() {
        return labelExpression;
    }

    public void setLabelExpression(String labelExpression) {
        this.labelExpression = labelExpression;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public int getMinScale() {
        return minScale;
    }

    public void setMinScale(int minScale) {
        this.minScale = minScale;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
    }

    public boolean isUseCodedValues() {
        return useCodedValues;
    }

    public void setUseCodedValues(boolean useCodedValues) {
        this.useCodedValues = useCodedValues;
    }
}

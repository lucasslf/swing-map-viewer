package br.lucasslf.gis.swingviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe ScaleHelper
 */
public class ScaleHelper extends GISAbstractBean {

    private List<Double> scales = new ArrayList<Double>();
    private int index = 0;

    
    public static ScaleHelper buildDefaultScaleHelper(){
        ScaleHelper scaleHelper = new ScaleHelper();
        scaleHelper.addScale(0.000000000050);
        scaleHelper.addScale(0.000000000075);
        scaleHelper.addScale(0.000000000100);
        scaleHelper.addScale(0.000000000250);
        scaleHelper.addScale(0.000000000500);
        scaleHelper.addScale(0.000000000750);
        scaleHelper.addScale(0.000000001000);
        scaleHelper.addScale(0.000000002500);
        scaleHelper.addScale(0.000000005000);
        scaleHelper.addScale(0.000000007500);
        scaleHelper.addScale(0.000000010000);
        scaleHelper.addScale(0.000000025000);
        scaleHelper.addScale(0.000000050000);
        scaleHelper.addScale(0.000000075000);
        scaleHelper.addScale(0.000000100000);
        scaleHelper.addScale(0.000000250000);
        scaleHelper.addScale(0.000000500000);
        scaleHelper.addScale(0.000000750000);
        scaleHelper.addScale(0.000001000000);
        scaleHelper.addScale(0.000002500000);
        scaleHelper.addScale(0.000005000000);
        scaleHelper.addScale(0.000007500000);
        scaleHelper.addScale(0.000010000000);
        scaleHelper.addScale(0.000025000000);
        scaleHelper.addScale(0.000050000000);
        scaleHelper.addScale(0.000075000000);
        scaleHelper.addScale(0.00010000000);
        scaleHelper.addScale(0.00025000000);
        scaleHelper.addScale(0.00050000000);
        scaleHelper.addScale(0.00075000000);
        scaleHelper.addScale(0.00100000000);
        scaleHelper.addScale(0.00250000000);
        scaleHelper.addScale(0.00500000000);
        scaleHelper.addScale(0.00750000000);
        scaleHelper.addScale(0.01000000000);
        scaleHelper.addScale(0.02500000000);
        scaleHelper.addScale(0.05000000000);
        scaleHelper.addScale(0.07500000000);
        scaleHelper.addScale(0.10000000000);
        scaleHelper.addScale(0.25000000000);
        scaleHelper.addScale(0.5);
        scaleHelper.resetToMiddle();
        return scaleHelper;
    }
    
    public void addScale(double scale) {
        scales.add(scale);
    }

    public Double getMinScale() {
        return scales.get(0);
    }


    public void setMaxScale() {
        index = scales.size()-1;
    }
    
    public Double getMaxScale() {
        return scales.get(scales.size()-1);
    }

    public Double getNextScale() {
        if (index < scales.size() - 1) {
            return scales.get(++index);
        } else {
            return null;
        }
    }

    public int getMaxIndex(){
        return scales.size() -1 ;
    }
    
    public void incrementScale(int increment) {
        setIndex(index + increment);
    }

    public Double getIncrementedScale(int increment) {
        int newIndex = index + increment;
        if (newIndex < scales.size() && newIndex > -1) {
            return scales.get(newIndex);
        } else {
            return null;
        }
    }


    public Double getScale() {
        return scales.get(index);
    }

    public void resetScales() {
        setIndex(0);
    }
    
    public int getScalesCount(){
        return scales.size();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        int old = this.index;
        this.index = index;
        firePropertyChange("index", old, index);
    }
    
    public void resetToMiddle() {
        setIndex(scales.size() / 2);
    }
}

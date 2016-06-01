package br.lucasslf.gis.swingviewer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class FeatureTable {

    private Map<String, List<Layer>> featuresMap = new HashMap<String, List<Layer>>();

    public Map<String, List<Layer>> getFeaturesMap() {
        return featuresMap;
    }

    public void setFeaturesMap(Map<String, List<Layer>> featuresMap) {
        this.featuresMap = featuresMap;
    }

    public void putFeature(String serviceId, Layer feature) {
        List<Layer> a = featuresMap.get(serviceId);
        if (a == null) {
            a = new ArrayList<Layer>();
            featuresMap.put(serviceId, a);
        }
        a.add(feature);
    }

    public List<Layer> getFeatures(String serviceId) {
        return featuresMap.get(serviceId);
    }

    public void putFeatures(String serviceId, List<Layer> features) {
        featuresMap.put(serviceId, features);
    }
}

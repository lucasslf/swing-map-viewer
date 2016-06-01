package br.lucasslf.gis.swingviewer.component.query;

import br.lucasslf.gis.swingviewer.component.SnarfMapViewer;
import br.lucasslf.gis.swingviewer.map.GenericMapService;
import br.lucasslf.gis.swingviewer.map.MapService;
import br.lucasslf.gis.swingviewer.model.Feature;
import br.lucasslf.gis.swingviewer.model.Geometry;
import br.lucasslf.gis.swingviewer.model.Layer;
import br.lucasslf.gis.swingviewer.model.QueryReturn;
import br.lucasslf.gis.swingviewer.model.TemporaryLayer;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class QueryPM implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * PropertyChangeSupport usado para implementar o padrÃ£o JavaBeans e ser
     * usado pela API de binding
     */
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<Layer> layers;
    private Layer selectedLayer;
    private SnarfMapViewer mapViewer;
    private String queryText;
    private Color color = Color.RED;
    private List<TemporaryLayer> temporaryLayers = new ArrayList<TemporaryLayer>();
    private int alpha = 100;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        firePropertyChange("alpha", null, null);
        System.out.println(alpha);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        firePropertyChange("color", null, null);
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
        firePropertyChange("queryText", null, null);
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
        firePropertyChange("layers", null, null);
    }

    public Layer getSelectedLayer() {
        return selectedLayer;
    }

    public void setSelectedLayer(Layer selectedLayer) {

        this.selectedLayer = selectedLayer;
        firePropertyChange("selectedLayer", null, null);
    }

    public SnarfMapViewer getMapViewer() {
        return mapViewer;
    }

    public void setMapViewer(SnarfMapViewer mapViewer) {
        this.mapViewer = mapViewer;
        List<Layer> aux = new ArrayList<Layer>();
        for (GenericMapService service : mapViewer.getOperationalLayers()) {
            if (service instanceof MapService) {
                for (Layer l : ((MapService) service).getLayers()) {
                    if (l.getLayerConfiguration().getCapabilities() != null && l.getLayerConfiguration().getCapabilities().contains("Query") && l.getLayerConfiguration().getType().equals("Feature Layer")) {
                        aux.add(l);

                    }
                }
            }

        }
        this.setLayers(aux);
    }

    public void performMapClean() {
        temporaryLayers.clear();
        mapViewer.setTemporaryLayers(null);
        mapViewer.repaint();
    }

    public void performQuery() {
        //Identificar de que mapa veio a layer selecionada;
        MapService layersOwnerService = null;
        QueryReturn q = null;
        for (GenericMapService service : mapViewer.getOperationalLayers()) {
            if (service instanceof MapService) {
                for (Layer l : ((MapService) service).getLayers()) {
                    if (selectedLayer.equals(l)) {
                        layersOwnerService = (MapService) service;
                    }
                }
            }

        }
        if (layersOwnerService != null) {
            q = layersOwnerService.getService().performQuery(layersOwnerService.getUrl(), layersOwnerService.getCoordinateSystemCode(), queryText, selectedLayer.getId());
            if (q != null) {
                TemporaryLayer tl = new TemporaryLayer();
                List<Geometry> geometries = new ArrayList<Geometry>();
                for (Feature f : q.getFeatures()) {
                    geometries.add(f.getGeometry());
                }
                tl.setColor(getColor());
                tl.setGeometries(geometries);

                tl.setAlpha(alpha / 100f);
                temporaryLayers.add(tl);

                mapViewer.setTemporaryLayers(temporaryLayers);
                mapViewer.repaint();
            }
        }
    }

    /**
     * Adiciona um listener das propriedades do bean. MÃ©todo padrÃ£o JavaBeans
     *
     * @param listener O listener a ser adicionado.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove um listener das propriedades do bean. MÃ©todo padrÃ£o JavaBeans
     *
     * @param listener O listener a ser removido.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Remove todos os listeners das propriedades do bean. MÃ©todo padrÃ£o
     * JavaBeans
     */
    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener propertyChangeListener : support.getPropertyChangeListeners()) {
            support.removePropertyChangeListener(propertyChangeListener);
        }
    }

    /**
     * ForÃ§a o evento de alteraÃ§Ã£o de propriedade
     *
     * @param prop Nome da propriedade JavaBean
     * @param oldValue Valor antigo da propriedade
     * @param newValue Valor novo da propriedade
     */
    protected void firePropertyChange(String prop, Object oldValue, Object newValue) {
        support.firePropertyChange(prop, oldValue, newValue);
    }

    /**
     * Instacia o support apÃ³s deserializaÃ§Ã£o ver:
     * http://download.oracle.com/javase/6/docs/platform/serialization/spec/input.html#2971
     *
     * @param stream Stream usado para leitura do objeto serializado
     * @throws IOException Erro na leitura
     * @throws ClassNotFoundException Classe nÃ£o encontrada
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        support = new PropertyChangeSupport(this);
    }
}

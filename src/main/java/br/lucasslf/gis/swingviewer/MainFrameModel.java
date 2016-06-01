package br.lucasslf.gis.swingviewer;

import br.lucasslf.gis.swingviewer.model.GISAbstractBean;
import java.awt.geom.Point2D;

/**
 * Classe MainFrameModel
 */
public class MainFrameModel extends GISAbstractBean {

    private Point2D position = new Point2D.Double(0,0);
    private Double scale = 0.000001;
    private String message;

    public Double getScale() {
        return scale;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        firePropertyChange("message", null, null);
    }
    
    
    

    public void setScale(Double scale) {
        Double old = this.scale;
        this.scale = scale;
        firePropertyChange("scale", old, scale);
        firePropertyChange("textScale", null, getTextScale());
    }
    
    public String getTextScale(){
        double s = 1/scale;
        return "1 : "+s;
    }
    
    public String getCoordinates() {
        return "N: "+position.getY()+" E: "+position.getX();
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        Point2D old = this.position;
        this.position = position;
        firePropertyChange("position", old, position);
        firePropertyChange("coordinates", null, getCoordinates());
    }
}

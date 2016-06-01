package br.lucasslf.gis.swingviewer.map;

import br.lucasslf.gis.rs.client.MapServerProxy;
import br.lucasslf.gis.swingviewer.component.MapLoadListener;
import br.lucasslf.gis.swingviewer.model.Configuration;
import br.lucasslf.gis.swingviewer.model.Extent;
import java.awt.Graphics2D;
import java.net.URL;

/**
 *
 */
public interface GenericMapService {

    void addMapLoadListener(MapLoadListener mapLoadListener);

    float getAlpha();

    String getId();

    MapServerProxy getService();

    URL getUrl();

    boolean isLoading();

    void setService(MapServerProxy service);

    void setUrl(URL url);

    void exportImage(Extent extent, Double scale, Graphics2D g);

    boolean isVisible();

    void setVisible(boolean visible);

    Configuration getConfig();
}

package br.lucasslf.gis.swingviewer.component;

import java.util.EventListener;

/**
 *
 */
public interface MapLoadListener extends EventListener{

    public void mapLoaded(MapLoadEvent e);
    
    public void mapLoading(MapLoadEvent e);
    
    public void tileLoaded(MapLoadEvent e);
    
    public void configLoaded(MapLoadEvent e);
}

package br.lucasslf.gis.swingviewer.component;

import java.util.EventObject;

/**
 *
 */
public class MapLoadEvent extends EventObject{
    
    
    Long id;
    
    
    public MapLoadEvent(Object source,Long id) {
        super(source);
        this.id= id;
    }

    public Long getId() {
        return id;
    }


    
    
    
}

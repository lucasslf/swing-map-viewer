package br.lucasslf.gis.rs.client;

import br.lucasslf.gis.swingviewer.map.BaseTiledMapService;
import br.lucasslf.gis.swingviewer.map.MapService;
import br.lucasslf.gis.swingviewer.map.ImageService;
import br.lucasslf.gis.swingviewer.model.BaseMapConfiguration;
import br.lucasslf.gis.swingviewer.model.MapConfiguration;
import br.lucasslf.gis.swingviewer.model.ImageConfiguration;
import br.lucasslf.gis.swingviewer.model.LayerConfiguration;
import br.lucasslf.gis.swingviewer.model.LayerLegend;
import br.lucasslf.gis.swingviewer.model.QueryReturn;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Classe MapServerProxy
 */
public interface MapServerProxy {

    Image export(URL serverUrl, Double xMin, Double yMin, Double xMax, Double yMax, Dimension size, String coordinateSystem, List<Integer> visibleLayers);
    Image exportImage(URL serverUrl, Double xMin, Double yMin, Double xMax, Double yMax, Dimension size, String coordinateSystem);
    File downloadTileImageFile(URL url, int level, int row, int column,String fileName);
    
    BaseMapConfiguration getBaseMapConfiguration(BaseTiledMapService baseMapService);
    MapConfiguration getMapConfiguration(MapService dynamicMapService);
    ImageConfiguration getImageConfiguration(ImageService imageService);
    LayerConfiguration getLayerConfiguration(MapService dynamicMapService,int layerId);
    List<LayerLegend> getLayerLegends(MapService dynamicMapService);
    public QueryReturn performQuery(URL url, String text, String coordinateSystem,int layerId);
}

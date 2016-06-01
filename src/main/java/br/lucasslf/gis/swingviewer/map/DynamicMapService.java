package br.lucasslf.gis.swingviewer.map;

import br.lucasslf.gis.swingviewer.model.Extent;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 */
public interface DynamicMapService extends GenericMapService {

    void cancelPendingLoad();

    void setExportImage(BufferedImage exportImage);

    void reload(Extent visibleExtent, double scale, int dpi, Dimension screenSize);

    void reload();
}

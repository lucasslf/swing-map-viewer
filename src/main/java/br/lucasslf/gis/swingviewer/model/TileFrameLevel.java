package br.lucasslf.gis.swingviewer.model;

import br.lucasslf.gis.swingviewer.utilities.MapUtils;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe TileFrameLevel
 */
public class TileFrameLevel {

    private int index;
    private double mapWidthInPixels;
    private double mapHeightInPixels;
    private int columnCount;
    private int rowCount;
    private double resolution;
    private int tileWidth;
    private int tileHeight;
    private Point2D origin;
    private Double scale;
    private Extent fullExtent;
    private List<TileFrame> tileFrames = Collections.synchronizedList(new LinkedList<TileFrame>());
    private String baseMapServiceID = null;
    private Log log = LogFactory.getLog(TileFrameLevel.class);

    public String getBaseMapServiceID() {
        return baseMapServiceID;
    }

    public void setBaseMapServiceID(String baseMapServiceID) {
        this.baseMapServiceID = baseMapServiceID;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point2D getOrigin() {
        return origin;
    }

    public void setOrigin(Point2D origin) {
        this.origin = origin;
    }

    public double getMapWidthInPixels() {
        return mapWidthInPixels;
    }

    public void setMapWidthInPixels(double mapWidthInPixels) {
        this.mapWidthInPixels = mapWidthInPixels;
    }

    public double getMapHeightInPixels() {
        return mapHeightInPixels;
    }

    public void setMapHeightInPixels(double mapHeightInPixels) {
        this.mapHeightInPixels = mapHeightInPixels;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public boolean isInitialized() {
        return true;//imagesMatrix != null;
    }

    public void initializeLevel() {
        /*
         for (int i = 0; i < columnCount; i++) {
         List<Image> lista =  new ArrayList<Image>();
               
         imagesMatrix.add(lista);
         }
         */
    }

    public boolean areAllTilesLoadedForExtent(Extent extent) {

        Point leftInfTile = getTilePosition(extent.getLeftInferiorCorner().getX(), extent.getLeftInferiorCorner().getY());
        Point rightSupTile = getTilePosition(extent.getRightSuperiorCorner().getX(), extent.getRightSuperiorCorner().getY());
        int firstColumn = leftInfTile.x;
        int lastColumn = rightSupTile.x;
        int firstRow = rightSupTile.y;
        int lastRow = leftInfTile.y;
        for (int i = firstColumn; i <= lastColumn; i++) {
            for (int j = firstRow; j <= lastRow; j++) {
                if (getTile(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Point[] getFirstAndLastTileForExtent(Extent extent) {
        return new Point[]{getTilePosition(extent.getLeftInferiorCorner().getX(), extent.getLeftInferiorCorner().getY()), getTilePosition(extent.getRightSuperiorCorner().getX(), extent.getRightSuperiorCorner().getY())};

    }

    public Point getTilePosition(double x, double y) {
        int column = (int) ((x - origin.getX()) / resolution / tileWidth);
        int row = (int) ((origin.getY() - y) / resolution / tileHeight);
        return new Point(column, row);
    }

    public synchronized TileFrame getTileFrame(int column, int row) {
        for (TileFrame tileFrame : tileFrames) {
            if (tileFrame.getColumn() == column && tileFrame.getRow() == row) {
                refreshTilePosition(tileFrame);
                return tileFrame;
            }
        }
        return null;
    }

    private synchronized void refreshTilePosition(TileFrame tileFrame) {
        tileFrames.remove(tileFrame);
        tileFrames.add(tileFrame);
    }

    public synchronized Image getTile(double x, double y) {
        int column = (int) ((x - origin.getX()) / resolution / tileWidth);

        int row = (int) ((origin.getY() - y) / resolution / tileHeight);
        TileFrame tf = getTileFrame(column, row);
        if (tf == null || tf.getImage() == null) {
            return null;
        } else {
            return tf.getImage();
        }
    }

    public Extent getFullExtent() {
        return fullExtent;
    }

    public void setFullExtent(Extent fullExtent) {
        this.fullExtent = fullExtent;
    }

    public synchronized TileFrame createNewTile(int column, int row) {
        TileFrame tf = new TileFrame(column, row, MapUtils.resolveTileFileName(index, column, row, baseMapServiceID));
        tf.visit();
        tileFrames.remove(tf);
        tileFrames.add(tf);

        return tf;
    }

    public synchronized void sanitizeTiles() {
        while (tileFrames.size() > 50) {
            tileFrames.remove(0);
        }
    }

    public synchronized void removeTile(int column, int row) {
        TileFrame tileToBeRemoved = getTileFrame(column, row);
        if (tileToBeRemoved != null) {
            tileFrames.remove(tileToBeRemoved);
        }

    }

    public synchronized Image getTile(int column, int row) {
        TileFrame tf = getTileFrame(column, row);
        if (tf == null || tf.getImage() == null) {
            return null;
        } else {
            return tf.getImage();
        }
    }
}

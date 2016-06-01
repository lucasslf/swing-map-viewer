package br.lucasslf.gis.swingviewer.model;

import java.awt.Image;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe TileFrame
 */
public class TileFrame {

    private int column;
    private int row;
    private Image image = null;
    private int visitCount;
    private long lastVisit;
    private String fileName;

    private Log log = LogFactory.getLog(TileFrame.class);
    
    public TileFrame(int column, int row, String fileName) {
        this.column = column;
        this.row = row;
        this.fileName = fileName;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Image getImage() {

        if (image == null) {
            if (fileName != null && !"".equals(fileName)) {
                File file = new File(fileName);
                if (file.exists()) {
                    try {
                        image = ImageIO.read(file);
                    } catch (Exception ex) {
                        log.error(ex);
                    }

                }
            }
        }
        return image;
    }

    public File getImageFile() {

        File file = null;
        if (fileName != null && !"".equals(fileName)) {
            file = new File(fileName);

        }
        return file;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public long getLastVisit() {
        return lastVisit;
    }

    @Override
    public String toString() {
        return "TileFrame{" + "column=" + column + ", row=" + row + ", visitCount=" + visitCount + ", lastVisit=" + lastVisit + '}';
    }
    

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * (hash + this.column+100);
        hash = 71 * (hash + this.row);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileFrame other = (TileFrame) obj;
        if (this.column != other.column) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        return true;
    }

    public void visit() {
        visitCount++;
        lastVisit = Calendar.getInstance().getTimeInMillis();
    }
}

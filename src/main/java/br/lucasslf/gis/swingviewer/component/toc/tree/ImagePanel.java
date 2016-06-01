package br.lucasslf.gis.swingviewer.component.toc.tree;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        setPreferredSize(new Dimension(25, 25));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, null); 
        }
    }
}

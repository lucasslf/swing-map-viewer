package br.lucasslf.gis.swingviewer.component.toc.tree;

import br.lucasslf.gis.swingviewer.model.Legend;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 */
public class MultipleLegendPanel extends JPanel {


    public MultipleLegendPanel(List<Legend> legends) {
        super();
        setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new EmptyBorder(10,10,10,10)));
        setLayout(new GridLayout(legends.size(),2,10,2));
        setBackground(Color.WHITE);
        for (Legend l : legends) {
            InputStream in = new ByteArrayInputStream(l.getImageData());
            JLabel label = new JLabel();
            label.setText(l.getLabel());
            ImagePanel imagePanel = new ImagePanel();
            
            try {
                BufferedImage b = ImageIO.read(in);
                imagePanel.setImage(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.add(label);
            this.add(imagePanel);
                   
        }
    }
}

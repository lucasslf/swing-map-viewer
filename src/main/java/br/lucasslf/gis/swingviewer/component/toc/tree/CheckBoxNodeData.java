/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2013 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */
package br.lucasslf.gis.swingviewer.component.toc.tree;

import br.lucasslf.gis.swingviewer.map.BaseTiledMapService;
import br.lucasslf.gis.swingviewer.map.DynamicMapService;
import br.lucasslf.gis.swingviewer.map.GenericMapService;
import br.lucasslf.gis.swingviewer.model.Layer;
import br.lucasslf.gis.swingviewer.model.LayerLegend;
import br.lucasslf.gis.swingviewer.model.Legend;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JTree;

/**
 * A tree node user object, for use with a {@link JTree}, that tracks whether it
 * is checked.
 * <p>
 * Thanks to John Zukowski for the <a
 * href="http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm"
 * >sample code</a> upon which this is based.
 * </p>
 *
 * @author Curtis Rueden
 * @see CheckBoxNodeEditor
 * @see CheckBoxNodeRenderer
 */
public class CheckBoxNodeData {

    private Object nodeData;
    private NodeType type;

    public CheckBoxNodeData(Object nodeData) {
        this.nodeData = nodeData;
        if (nodeData instanceof DynamicMapService) {
            type = NodeType.OPERATIONAL_LAYER;
        } else if (nodeData instanceof BaseTiledMapService) {
            type = NodeType.BASE_MAP;
        } else if (nodeData instanceof Layer) {
            type = NodeType.FEATURE;

        } else {

            throw new RuntimeException("Unsupported nodeData Type " + nodeData.getClass());
        }

    }

    public void reverseCheck() {
        if (type == NodeType.FEATURE) {
            ((Layer) nodeData).setVisible(!((Layer) nodeData).isVisible());
        } else {
            ((GenericMapService) nodeData).setVisible(!((GenericMapService) nodeData).isVisible());
        }
    }

    public void setDefaultChecked() {
        if (type == NodeType.FEATURE) {
            ((Layer) nodeData).setVisible(((Layer) nodeData).isDefaultVisibility());
        } else {
            ((GenericMapService) nodeData).setVisible(true);
        }
    }

    public void setChecked(boolean checked) {
        if (type == NodeType.FEATURE) {
            ((Layer) nodeData).setVisible(checked);
        } else {
            ((GenericMapService) nodeData).setVisible(checked);
        }
    }

    public Object getNodeData() {
        return nodeData;
    }

    public boolean isChecked() {
        if (type == NodeType.FEATURE) {
            return ((Layer) nodeData).isVisible();
        } else {
            return ((GenericMapService) nodeData).isVisible();
        }

    }

    public String getText() {
        if (type == NodeType.FEATURE) {
            return ((Layer) nodeData).getName();
        } else {
            return ((GenericMapService) nodeData).getConfig().getName();
        }
    }

    public List<Legend> getLegends() {
        if (type == NodeType.FEATURE) {
            return ((Layer) nodeData).getLegends();
        } else {
            return null;
        }
    }


    @Override
    public String toString() {
        return getClass().getName() + "[" + type + "]";
    }
}

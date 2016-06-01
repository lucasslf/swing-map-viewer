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

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * A {@link TreeCellEditor} for reverseCheck box tree nodes.
 * <p>
 * Thanks to John Zukowski for the <a
 * href="http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm"
 * >sample code</a> upon which this is based.
 * </p>
 *
 * @author Curtis Rueden
 */
public class CheckBoxNodeEditor extends AbstractCellEditor implements
        TreeCellEditor {

    private final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
    private final JTree theTree;
    private CheckBoxNodeData data;
    private DefaultMutableTreeNode lastNode;

    public CheckBoxNodeEditor(final JTree tree) {
        theTree = tree;
    }

    private void checkAllChildren(DefaultMutableTreeNode rootNode, boolean value) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) rootNode.getFirstChild();

        while (n != null) {
            if (!n.isLeaf()) {
                checkAllChildren(n, value);
            }
            if (value) {
                ((CheckBoxNodeData) ((DefaultMutableTreeNode) n).getUserObject()).setDefaultChecked();
            } else {
                ((CheckBoxNodeData) ((DefaultMutableTreeNode) n).getUserObject()).setChecked(value);
            }
            n = (DefaultMutableTreeNode) rootNode.getChildAfter(n);

        }

    }

    @Override
    public Object getCellEditorValue() {
        data.reverseCheck();
        if (!lastNode.isLeaf()) {
            checkAllChildren(lastNode, data.isChecked());
            theTree.repaint();
        }
        return data;
    }

    @Override
    public boolean isCellEditable(final EventObject event) {
        if (!(event instanceof MouseEvent)) {
            return false;
        }
        final MouseEvent mouseEvent = (MouseEvent) event;

        final TreePath path =
                theTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
        if (path == null) {
            return false;
        }

        final Object node = path.getLastPathComponent();
        if (!(node instanceof DefaultMutableTreeNode)) {
            return false;
        }
        final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;

        final Object userObject = treeNode.getUserObject();
        return userObject instanceof CheckBoxNodeData;
    }

    @Override
    public Component getTreeCellEditorComponent(final JTree tree,
            final Object value, final boolean selected, final boolean expanded,
            final boolean leaf, final int row) {
        lastNode = (DefaultMutableTreeNode) value;
        data = (CheckBoxNodeData) lastNode.getUserObject();
        final Component editor =
                renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
                row, true);
        // editor always selected / focused
        final ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent itemEvent) {
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        };
        if (editor instanceof CheckBoxNodePanel) {
            final CheckBoxNodePanel panel = (CheckBoxNodePanel) editor;
            panel.check.addItemListener(itemListener);
        }

        return editor;
    }
}

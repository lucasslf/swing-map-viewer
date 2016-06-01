package br.lucasslf.gis.swingviewer.component.toc.tree;

import br.lucasslf.gis.swingviewer.component.SnarfMapViewer;
import br.lucasslf.gis.swingviewer.map.GenericMapService;
import br.lucasslf.gis.swingviewer.map.MapService;
import br.lucasslf.gis.swingviewer.model.Layer;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 */
public class TOCTree extends JTree {

    public TOCTree() {
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    public TOCTree(Object[] value) {
        super(value);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);

    }

    public TOCTree(Vector<?> value) {
        super(value);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    public TOCTree(Hashtable<?, ?> value) {
        super(value);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    public TOCTree(TreeNode root) {
        super(root);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    public TOCTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    public TOCTree(TreeModel newModel) {
        super(newModel);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        this.setRootVisible(false);
    }

    class LayerStackObject {

        DefaultMutableTreeNode node = null;
        Layer layer = null;
    }

    public TOCTree(final SnarfMapViewer viewer) {
        super();
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Map Viewer");

        if (viewer.getBaseTiledMapService() != null) {
            createServiceTree(viewer.getBaseTiledMapService(), root);
        }
        for (GenericMapService service : viewer.getOperationalLayers()) {
            createServiceTree(service, root);

        }
        final DefaultTreeModel newTreeModel = new DefaultTreeModel(root);
        this.setModel(newTreeModel);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        treeModel.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(final TreeModelEvent e) {
                viewer.reloadBuffers();
                viewer.repaint();
            }

            @Override
            public void treeNodesInserted(final TreeModelEvent e) {
            }

            @Override
            public void treeNodesRemoved(final TreeModelEvent e) {
            }

            @Override
            public void treeStructureChanged(final TreeModelEvent e) {
            }
        });
    }

    public TOCTree(GenericMapService... services) {
        super();

        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Map Viewer");

        for (GenericMapService service : services) {
            createServiceTree(service, root);

        }
        final DefaultTreeModel newTreeModel = new DefaultTreeModel(root);
        this.setModel(newTreeModel);
        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        this.setCellRenderer(renderer);
        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(this);
        this.setCellEditor(editor);
        this.setEditable(true);
        //this.setRootVisible(false);
/*
         // listen for changes in the model (including reverseCheck box toggles)
         treeModel.addTreeModelListener(new TreeModelListener() {
         @Override
         public void treeNodesChanged(final TreeModelEvent e) {
         Object[] path = e.getTreePath().getPath();
         }

         @Override
         public void treeNodesInserted(final TreeModelEvent e) {
         }

         @Override
         public void treeNodesRemoved(final TreeModelEvent e) {
         }

         @Override
         public void treeStructureChanged(final TreeModelEvent e) {
         }
         });*/
    }

    private void createServiceTree(GenericMapService service, DefaultMutableTreeNode root) {
        DefaultMutableTreeNode node = createNode(root, service);

        if (service instanceof MapService) {
            MapService mapService = (MapService) service;
            Stack<LayerStackObject> auxStack = new Stack<LayerStackObject>();
            LayerStackObject rootObject = new LayerStackObject();
            rootObject.node = node;
            auxStack.push(rootObject);
            for (Layer l : mapService.getLayers()) {
                LayerStackObject so = new LayerStackObject();
                so.layer = l;
                while (auxStack.peek().layer != null && auxStack.peek().layer.getId() != l.getParentLayerId()) {
                    auxStack.pop();
                }

                so.node = createNode(auxStack.peek().node, l);

                auxStack.push(so);
            }
        }
    }

    private DefaultMutableTreeNode createNode(
            final DefaultMutableTreeNode parent, Object dataO) {
        final CheckBoxNodeData data = new CheckBoxNodeData(dataO);
        final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
        if (parent != null) {
            parent.add(node);
        }
        return node;
    }
}

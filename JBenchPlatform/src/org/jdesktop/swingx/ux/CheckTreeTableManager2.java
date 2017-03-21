/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx.ux;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;

/**
 * 与CheckTreeTableManager的不同在于,CheckTreeTableManager2通过
 * 监听TreeSelectionListener来选中树节点之前的checkbox
 *
 * @author vearn
 */
public class CheckTreeTableManager2 implements TreeSelectionListener {

    private CheckTreeSelectionModel selectionModel;
    private JXTreeTable treetable;
    private JTree tree;

    public CheckTreeTableManager2(JXTreeTable treeTable) {
        this.treetable = treeTable;
        this.tree = (JTree) treeTable.getCellRenderer(0, 0);
        selectionModel = new CheckTreeSelectionModel(tree.getModel());
        tree.setCellRenderer(new DefaultTreeRenderer(new CheckTreeCellProvider(selectionModel)));
        treeTable.addTreeSelectionListener(this);
    }

    public CheckTreeSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        if (path == null) {
            return;
        }
        boolean selected = selectionModel.isPathSelected(path, true);

        try {
            if (selected) {
                selectionModel.removeSelectionPath(path);
            } else {
                selectionModel.addSelectionPath(path);
            }
        } finally {
            treetable.repaint();
        }
    }
}
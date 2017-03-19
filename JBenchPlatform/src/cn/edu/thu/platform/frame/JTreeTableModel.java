package cn.edu.thu.platform.frame;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.*;
import org.jdesktop.swingx.decorator.HighlightPredicate.ColumnHighlightPredicate;
import org.jdesktop.swingx.painter.CheckerboardPainter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.treetable.*;
import java.awt.Font;
public class JTreeTableModel extends JXTreeTable{
	public JTreeTableModel(){
		setFont(new Font("Century Gothic", Font.PLAIN, 22));
		setTreeTableModel(new RandomTextTreeTableModel(5));
		setRowSelectionAllowed(true);

		setDragEnabled(true);
		setEditable(true);
		setTreeCellRenderer(new DefaultTreeRenderer());
		setSelectionBackground(new Color(176, 197, 227));
		setSelectionForeground(new Color(0, 0, 128));

		BorderHighlighter border = new BorderHighlighter(new ColumnHighlightPredicate(1, 2), BorderFactory.createEmptyBorder(0, 6, 0, 6), false, false);
		addHighlighter(border);


	}
	@Override
	public TableCellEditor getCellEditor(int row, int column){
		System.out.println(column);
		return new TableCellEditor() {

			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				//throw new UnsupportedOperationException("Not supported yet.");
				return new JTextField();
			}

			public Object getCellEditorValue() {
				//throw new UnsupportedOperationException("Not supported yet.");
				return "";
			}

			public boolean isCellEditable(EventObject anEvent) {
				//throw new UnsupportedOperationException("Not supported yet.");
				return true;
			}

			public boolean shouldSelectCell(EventObject anEvent) {
				//throw new UnsupportedOperationException("Not supported yet.");
				return true;
			}

			public boolean stopCellEditing() {
				//throw new UnsupportedOperationException("Not supported yet.");
				return false;
			}

			public void cancelCellEditing() {
				//throw new UnsupportedOperationException("Not supported yet.");

			}

			public void addCellEditorListener(CellEditorListener l) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			public void removeCellEditorListener(CellEditorListener l) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		};
	}
}
class RandomTextTreeTableModel extends DefaultTreeTableModel {

	DefaultMutableTreeTableNode Root = null;

	RandomTextTreeTableModel(int i) {
		Root = new DefaultMutableTreeTableNode();
		this.setRoot(Root);
		for (int x = 0; x < 5; x++) {
			DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode("HI");
			Root.add(node);
			for (int j = 0; j < i; j++) {
				node.add(new DefaultMutableTreeTableNode(j));
			}
		}
	}

	@Override
	public String getColumnName(int column){
		return "dsdsddds";
	}

	@Override
	public int getColumnCount() {
		return 6;
	}


	@Override
	public Object getValueAt(Object arg0, int arg1) {
		if (arg1 == 1) {
			return new String("hi " + arg0.toString());
		}
		return arg0;
	}
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.thu.platform.frame;

import javax.swing.event.TreeModelListener;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 *
 * @author vearn
 */
public class MyTreeTableModel extends DefaultTreeTableModel {

    private String[] _names = {"", "Name", "Line1", "Line2", "Variable","Details"};//,"Details"
    private Class[] _types = {Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};

    public MyTreeTableModel(TreeTableNode node) {
        super(node);
    }

	/**
     * 列的类型
     */
    @Override
    public Class getColumnClass(int col) {
        return _types[col];
    }

    /**
     * 列的数量
     */
    @Override
    public int getColumnCount() {
        return _names.length;
    }

    /**
     * 表头显示的内容
     */
    @Override
    public String getColumnName(int column) {
        return _names[column];
    }

    /**
     * 返回在单元格中显示的Object
     */
    @Override
    public void setValueAt(Object aValue, Object node, int col) {  
        if (node instanceof DefaultMutableTreeTableNode) {  
            DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;  
            Object o = mutableNode.getUserObject();  
            if (o != null && o instanceof MyTreeTableNode) {  
                MyTreeTableNode bean = (MyTreeTableNode) o;  
                switch (col) {  
                    case 0:  
                        String tmp = (String) aValue;  
                        bean.setName(tmp);  
                        break;  
                    case 1:  
                    	String v1 = (String) aValue;  
                        bean.setValue1(v1);  
                        break;  
                    case 2:  
                    	String v2 = (String) aValue;  
                        bean.setValue2(v2);  
                        break;  
                    case 3:  
                    	String v3 = (String) aValue;  
                        bean.setValue3(v3);  
                        break;  
                    case 4:  
                    	String v4 = (String) aValue;  
                        bean.setValue4(v4);  
                        break;  
                    case 5:  
                    	String v5 = (String) aValue;  
                        bean.setValue5(v5);  
                        break;  
                }  
            }  
        }  
    }

  public Object getValueAt(Object node, int column) {
      Object value = null;
      if (node instanceof DefaultMutableTreeTableNode) {
          DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;
          Object o = mutableNode.getUserObject();
          if (o != null && o instanceof MyTreeTableNode) {
              MyTreeTableNode bean = (MyTreeTableNode) o;
              switch (column) {
                  case 0:
                      value = bean.getName();
                      break;
                  case 1:
                      value = bean.getValue1();
                      break;
                  case 2:
                      value = bean.getValue2();
                      break;
                  case 3:
                      value = bean.getValue3();
                      break;
                  case 4:
                      value = bean.getValue4();
                      break;
                  case 5:
                      value = bean.getValue5();
                      break;
              }
          }
      }
      return value;
	}
    /**
     * 设置所有单元格都不能编辑
     *
     * @param the node (i.e. row) for which editing is to be determined
     * @param the column for which editing is to be determined
     * @return false
     */
    @Override
    public boolean isCellEditable(Object node, int column) {
    	if(column==0) {
    		return false;
    	}
        return true;
    }
}

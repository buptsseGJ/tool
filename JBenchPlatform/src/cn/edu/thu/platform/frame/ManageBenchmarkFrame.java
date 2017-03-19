/**
 * 
 */
package cn.edu.thu.platform.frame;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * @author yx
 *
 */
public class ManageBenchmarkFrame extends JFrame {
	private JTreeTableModel table;
	public ManageBenchmarkFrame() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar);
		
		table = new JTreeTableModel();
		getContentPane().add(table, BorderLayout.CENTER);
	}

}

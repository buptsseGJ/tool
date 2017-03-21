/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.thu.platform.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.ux.CheckTreeTableManager2;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.frame.MyTreeTableNode;
import cn.edu.thu.platform.parser.DomToEntity;
import cn.edu.thu.platform.parser.MyErrorHandler;
import cn.edu.thu.platform.parser.ParseXml;

import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;


import java.awt.SystemColor;

/**
 *
 * @author vearn
 */
public class ManageBenchmarkFrame extends JFrame  implements MouseListener,ActionListener{

	private final CheckTreeTableManager2 manager;
	public JXTreeTable treetable;
	private JPopupMenu popMenu; 
	private int curClick_X = -1,curClick_Y = -1;
	JMenuItem addItem1 = new JMenuItem("add usecase"); 
	JMenuItem addItem2 = new JMenuItem("add race");  
	JMenuItem delItem1 = new JMenuItem("delete usecase");  
	JMenuItem delItem2 = new JMenuItem("delete race");  

	public ManageBenchmarkFrame() {
		super("management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		treetable = new JXTreeTable(new MyTreeTableModel(createDummyData())) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					@Override
					public void setDraggedColumn(TableColumn aColumn) {
						if (aColumn == getColumnModel().getColumn(0)) {
							return;
						}
						super.setDraggedColumn(aColumn);
					}
				};
			}

			//底板颜色
			@Override
			public Color getBackground() {
				Color c = super.getBackground();
				return c;//Color.green;
			}			
			protected TableColumnModel createDefaultColumnModel() {
				return new DefaultTableColumnModel() {
					public void moveColumn(int columnIndex, int newIndex) {
						if (columnIndex == 0 || newIndex == 0) {
							return;
						}
						super.moveColumn(columnIndex, newIndex);
					}
				};
			}
		};
		treetable.setHorizontalScrollEnabled(true);
		manager = new CheckTreeTableManager2(treetable);   //  构造checkboxtreetable、紧跟JXTreeTable构造方法。否则...
		treetable.getColumnModel().getColumn(0).setPreferredWidth(170); //  设置第一列宽度
		treetable.getColumnModel().getColumn(1).setPreferredWidth(220); //  设置第一列宽度
		treetable.getColumnModel().getColumn(2).setPreferredWidth(70); //  设置第一列宽度
		treetable.getColumnModel().getColumn(3).setPreferredWidth(70); //  设置第一列宽度
		treetable.getColumnModel().getColumn(4).setPreferredWidth(110); //  设置第一列宽度
		treetable.getColumnModel().getColumn(5).setPreferredWidth(600); //  设置第一列宽度
		treetable.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_OFF);
		treetable.setRootVisible(false); // don't show the root point
		//treetable.expandAll();  //  展开全部节点
		treetable.collapseAll();  //  折叠全部节点
		treetable.expandRow(0);  //  展开全部节点
		treetable.setHighlighters(HighlighterFactory.createSimpleStriping());//exchange the single line's background color
		treetable.getTableHeader().setReorderingAllowed(false);//do not allow users to exhcange two columns    
		treetable.setToolTipText("");
		treetable.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 23));
		treetable.getTableHeader().setSize(getWidth(), 100);		
		Dimension size = treetable.getTableHeader().getPreferredSize();
		size.height = 50;//设置新的表头高度
		treetable.getTableHeader().setPreferredSize(size);
		treetable.setRowHeight(35);
		treetable.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		treetable.setSelectionBackground(new Color(179,222,255));
		treetable.setSelectionForeground(Color.DARK_GRAY);
		JScrollPane JSP= new JScrollPane(treetable);
		getContentPane().add(JSP,BorderLayout.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBackground(new Color(255, 255, 205));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaptionBorder);
		panel_1.setSize(300, 50);
		panel.add(panel_1,BorderLayout.CENTER);
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(SystemColor.inactiveCaptionBorder);
		toolBar.setForeground(Color.DARK_GRAY);
		toolBar.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		//toolBar.setLocation(10,getWidth()-100);
		toolBar.setLayout(new FlowLayout());
		JButton button1 = new JButton("Save   ");
		button1.setVerticalAlignment(SwingConstants.TOP);
		button1.setForeground(Color.DARK_GRAY);
		button1.setBackground(SystemColor.inactiveCaptionBorder);
		button1.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		button1.setFocusable(false);
		button1.setIcon(new ImageIcon(ManageBenchmarkFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		toolBar.add(button1);
		JButton button2 = new JButton("Cancle  ");
		button2.setBackground(SystemColor.inactiveCaptionBorder);
		button2.setForeground(Color.DARK_GRAY);
		button2.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		button2.setFocusable(false);
		button2.setIcon(new ImageIcon(ManageBenchmarkFrame.class.getResource("/org/jdesktop/swingx/plaf/basic/resources/clear.gif")));
		toolBar.add(button2);
		panel.add(toolBar, BorderLayout.EAST);
		getContentPane().add(panel,BorderLayout.NORTH);

		treetable.addMouseListener(this);  

		setForeground(Color.DARK_GRAY);
		setFont(new Font("Century Gothic", Font.PLAIN, 22));
		popMenu = new JPopupMenu();  
		popMenu.add(addItem1); 
		popMenu.add(addItem2);  
		popMenu.add(delItem1);  
		popMenu.add(delItem2);
		//add usecase
		addItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curClick_X>0&&curClick_Y>0) {
					TreePath path = treetable.getPathForLocation(curClick_X,curClick_Y);
					DefaultMutableTreeTableNode curNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
					if(!curNode.isLeaf()) {
						int nodeNum = curNode.getParent().getChildCount();
						MyTreeTableNode leaf = new MyTreeTableNode("UseCase"+(nodeNum));
						DefaultMutableTreeTableNode tmp = new DefaultMutableTreeTableNode(leaf);
						((DefaultMutableTreeTableNode) path.getParentPath().getLastPathComponent()).add(tmp);
					}else {
						int nodeNum = curNode.getParent().getParent().getChildCount();
						MyTreeTableNode leaf = new MyTreeTableNode("UseCase"+(nodeNum));
						DefaultMutableTreeTableNode tmp = new DefaultMutableTreeTableNode(leaf);
						((DefaultMutableTreeTableNode) path.getParentPath().getParentPath().getLastPathComponent()).add(tmp);
					}
					treetable.updateUI();
				}
			}

		});  
		//add race
		addItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curClick_X>0&&curClick_Y>0) {
					TreePath path = treetable.getPathForLocation(curClick_X,curClick_Y);
					DefaultMutableTreeTableNode curNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
					if(!curNode.isLeaf()) {
						int nodeNum = curNode.getChildCount();
						String parentID = ((MyTreeTableNode)curNode.getUserObject()).getName();
						MyTreeTableNode leaf = new MyTreeTableNode("race"+(parentID.substring(8,9))+"_"+(nodeNum+1));
						DefaultMutableTreeTableNode tmp = new DefaultMutableTreeTableNode(leaf);
						curNode.add(tmp);
					}else {
						int nodeNum = curNode.getParent().getChildCount();
						String parentID = ((MyTreeTableNode)curNode.getUserObject()).getName();
						MyTreeTableNode leaf = new MyTreeTableNode("race"+(parentID.substring(4,5))+"_"+(nodeNum+1));
						DefaultMutableTreeTableNode tmp = new DefaultMutableTreeTableNode(leaf);
						((DefaultMutableTreeTableNode) path.getParentPath().getLastPathComponent()).add(tmp);
					}
					treetable.updateUI();
				}
			}

		});  
		//delete UseCase Function
		delItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curClick_X>0&&curClick_Y>0) {
					TreePath path = treetable.getPathForLocation(curClick_X,curClick_Y);
					DefaultMutableTreeTableNode toBeDeletedNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
					treetable.getTreeSelectionModel().clearSelection();
					toBeDeletedNode.removeFromParent();
				}
				treetable.updateUI();
			}
		}); 
		delItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curClick_X>0&&curClick_Y>0) {
					TreePath path = treetable.getPathForLocation(curClick_X,curClick_Y);
					DefaultMutableTreeTableNode toBeDeletedNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
					treetable.getTreeSelectionModel().clearSelection();
					toBeDeletedNode.removeFromParent();
				}
				treetable.updateUI();
			}
		});  
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});  

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateStaticVariables();
			}
		});  
	}

	public void updateStaticVariables(){
		boolean isSave = true;
		int pathIndex=0;
		Map<String,Set<Race>> reports = new HashMap<String,Set<Race>>();
		TreePath rootPath = treetable.getPathForLocation(0, 0).getParentPath();
		DefaultMutableTreeTableNode rootNode = (DefaultMutableTreeTableNode)rootPath.getLastPathComponent();
		int caseNum = rootNode.getChildCount();
		for(int i=0;i<caseNum;i++) {
			pathIndex++;
			MyTreeTableNode curUseCaseMyTreeNode = (MyTreeTableNode)rootNode.getChildAt(i).getUserObject();

			if(curUseCaseMyTreeNode.getValue1().contains("<")||curUseCaseMyTreeNode.getValue1().contains(">")||curUseCaseMyTreeNode.getValue1().contains("&")||curUseCaseMyTreeNode.getValue1().contains("'")||curUseCaseMyTreeNode.getValue1().contains("\"")){
				JOptionPane.showMessageDialog(null, "Usecase name can't contain '<','>','&',''','\"'");
				isSave = false;
				break;
			}else if(reports.containsKey(curUseCaseMyTreeNode.getValue1())){//用例名重复
				JOptionPane.showMessageDialog(null, "Usecase name must be unique !");
				isSave = false;
				break;
			}else if(curUseCaseMyTreeNode.getValue1().equals("")){//用例名为空
				JOptionPane.showMessageDialog(null, "Usecase name can't be null !");
				isSave = false;
				break;
			}else {
				Set<Race> races = new HashSet<Race>();
				DefaultMutableTreeTableNode curUseCaseNode = (DefaultMutableTreeTableNode)  treetable.getPathForRow(pathIndex-1).getLastPathComponent();
				for(int j=0;j<curUseCaseNode.getChildCount();j++) {
					pathIndex++;
					MyTreeTableNode curRaceNode = (MyTreeTableNode)curUseCaseNode.getChildAt(j).getUserObject();
					Race newRace = new Race(curRaceNode.getValue2(), curRaceNode.getValue3(), curRaceNode.getValue4(), curRaceNode.getValue1(), curRaceNode.getValue5());
					if(!isValidate(newRace)) {//内容存在问题
						treetable.getTreeSelectionModel().setSelectionPath(treetable.getPathForLocation(pathIndex, 0));
						isSave = false;
						break;
					}else if(curRaceNode.getValue3().equals("")||curRaceNode.getValue2().equals("")){//行号为空
						JOptionPane.showMessageDialog(null, "line number can't be null !");
						treetable.getTreeSelectionModel().setSelectionPath(treetable.getPathForLocation(pathIndex, 0));
						isSave = false;
						break;
					}else {
						//判断是否与已确认的Race重复
						Iterator it = races.iterator();
						while(it.hasNext()){
							Race race = (Race) it.next();
							if(race.getLine1().equals(curRaceNode.getValue3())&&race.getLine2().equals(curRaceNode.getValue4())
									||race.getLine2().equals(curRaceNode.getValue3())&&race.getLine1().equals(curRaceNode.getValue4())){

								JOptionPane.showMessageDialog(null, "race重复！");
								isSave = false;
								break;
							}
						}
						if(isSave) {
							races.add(newRace);
						}else {
							break;
						}
					}
				}
				if(isSave) {	
					if(races.size()>0)
						reports.put(curUseCaseMyTreeNode.getValue1(), races);
				}else {
					break;
				}
			}
		}
		if(isSave) {//全部符合条件，保存到文件	
			//清空表单内的所有元素，防止下次打开时出现异常
//			treetable.getTreeSelectionModel().clearSelection();
//			for(int i=caseNum-1;i>=0;i--) {
//				((DefaultMutableTreeTableNode)rootNode.getChildAt(i)).removeFromParent();
//			}
//			treetable.updateUI();
			
			//从内存中将所有的Element删除
			ParseXml parse = new ParseXml();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setValidating(true);
		    factory.setExpandEntityReferences(false);
			Document doc = null;
			try {
				doc = parse.buildDocument("");
			} catch (SAXException | IOException e1) {
				e1.printStackTrace();
			}
			for(int i=0;i<Reports.programNames.size();i++) {
				Report re = Reports.reports.get(Reports.programNames.get(i));
				if (re != null) {
					Set<Race> races = re.getRaces();
					Iterator<Race> reIt = races.iterator();
					while (reIt.hasNext()) {
						parse.deleteElement(doc,Reports.programNames.get(i),reIt.next());
					}
				}
			}
			Iterator<Entry<String, Set<Race>>> iter = reports.entrySet().iterator();
			while (iter.hasNext()) {	
//				try {
//					doc = parse.buildDocument("");
//				} catch (SAXException | IOException e1) {
//					e1.printStackTrace();
//				}
				Map.Entry<String, Set<Race>> entry = (Map.Entry<String, Set<Race>>) iter.next();
				Set<Race> tmpRaces = entry.getValue();
				String tmpCaseName = entry.getKey();
				Iterator<Race> it = tmpRaces.iterator();
				while (it.hasNext()) {
					try {
						parse.addElement(doc, tmpCaseName, it.next());
						parse.writeDomToXml(doc);
					} catch (TransformerException e) {//| SAXException | IOException e) {
						e.printStackTrace();
					}
				}
			}
			Reports.removeAllBenchmakrs();
			treetable.removeAll();
			ParseXml parser = new ParseXml();
			Document validationResult = parser.validateXml("file/brench1.xml");
			if (validationResult != null) {
				System.out.println("正确");
				Reports.removeAllBenchmakrs();
				DomToEntity convert = new DomToEntity();
				convert.startDom(validationResult);
			} else {
				System.out.println("错误");
			}
			treetable.updateUI();
			JOptionPane.showMessageDialog(null, "Benchmarks 更新成功 ！");
			
			dispose();
			
		}
	}
	
	public boolean isValidate(Race race){
		String line1 = race.getLine1();
		String line2 = race.getLine2();
		String variable = race.getVariable();
		String packageClass = race.getPackageClass();
		if(line1.contains("<")||line1.contains(">")||line1.contains("&")||line1.contains("'")||line1.contains("\"")){
			JOptionPane.showMessageDialog(null, "line1不能包含'<','>','&',''','\"'");
			return false;
		}else if(line2.contains("<")||line2.contains(">")||line2.contains("&")||line2.contains("'")||line2.contains("\"")){
			JOptionPane.showMessageDialog(null, "line2不能包含'<','>','&',''','\"'");
			return false;
		}else if(variable.contains("<")||variable.contains(">")||variable.contains("&")||variable.contains("'")||variable.contains("\"")){
			JOptionPane.showMessageDialog(null, "variable不能包含'<','>','&',''','\"'");
			return false;
		}else if(packageClass.contains("<")||packageClass.contains(">")||packageClass.contains("&")||packageClass.contains("'")||packageClass.contains("\"")){
			JOptionPane.showMessageDialog(null, "packageClass不能包含'<','>','&',''','\"'");
			return false;
		}
		return true;
	}		
	/**
	 * 构造一个treetablemodel并返回根结点
	 *
	 * @return 根结点
	 */
	Map<String, Report> localReport = new HashMap<String, Report>();
	private JToolBar toolBar;
	private DefaultMutableTreeTableNode createDummyData() {
		
		DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(new MyTreeTableNode("root"));

		for (int i = 0; i < Reports.programNames.size(); i++) {
			Report reports = Reports.reports.get(Reports.programNames.get(i));
			
			MyTreeTableNode tb = new MyTreeTableNode("UseCase_"+i,reports.getName(),"","","","");
			DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode(tb);

			if (reports != null) {
				Set<Race> races = reports.getRaces();
				Race race = null;
				int count = 0;
				Iterator<Race> it = races.iterator();
				while (it.hasNext()) {
					count++;
					race = (Race) it.next();
					MyTreeTableNode leaf = new MyTreeTableNode("race"+i+"_"+count,race.getPackageClass(),race.getLine1(),race.getLine2(),race.getVariable(),race.getDetail());
					DefaultMutableTreeTableNode tmp = new DefaultMutableTreeTableNode(leaf);
					node.add(tmp);
				}
			}
			root.add(node);
		}
		return root;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		TreePath path = treetable.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用  
		if (path == null) {  
			return;  
		}  
		treetable.getTreeSelectionModel().setSelectionPath(path);//.setSelectionPath(path);  
		if (e.getButton() == 3) { 
			curClick_X=e.getX();
			curClick_Y=e.getY();
			if(((DefaultMutableTreeTableNode)path.getLastPathComponent()).isLeaf()) {
				popMenu.remove(delItem1);
				popMenu.add(delItem2);
			}else {
				popMenu.remove(delItem2);
				popMenu.add(delItem1);
			}
			popMenu.show(treetable, e.getX(), e.getY());  
		} 
	}
}
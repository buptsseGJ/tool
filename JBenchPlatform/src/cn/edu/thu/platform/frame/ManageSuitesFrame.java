package cn.edu.thu.platform.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;

public class ManageSuitesFrame extends JFrame {
	private String[] programName = {"nihao","nihaoma","1","2","3","4","5","6","7","8","9"};
//
	private JList jListLeft = new JList(programName);
	private JTextArea jTextRight = new JTextArea();	
	private JScrollPane jscLeft = new JScrollPane(jListLeft);
	private JScrollPane jscRight = new JScrollPane(jTextRight);
	public ManageSuitesFrame(){
		this.setLayout(null);
		jListLeft.setVisibleRowCount(15);
		jTextRight.setLineWrap(true);
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		JPanel panelBottom = new JPanel();
		jListLeft.setFixedCellWidth(350);
		jListLeft.setFixedCellHeight(30);
		jListLeft.setFont(new Font("Arial",Font.BOLD,25));
		jListLeft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTextRight.setPreferredSize(new Dimension(380,580));
		jTextRight.setFont(new Font("Arial",Font.BOLD,15));
//		jscLeft.setPreferredSize(380);
		panelLeft.setSize(400, 600);
		panelRight.setSize(400, 600);
		panelLeft.setBounds(new Rectangle(50,50,400,600));
		panelRight.setBounds(new Rectangle(550,50,400,600));
		panelLeft.add(jscLeft);
		panelRight.add(jscRight);
		panelLeft.setBackground(Color.blue);
		panelRight.setBackground(Color.gray);
		JButton button = new JButton("nihao");
//		panelBottom.setSize(800, 150);
		panelBottom.setBounds(new Rectangle(150,850,600,100));
		panelBottom.add(button);
//		this.add(panelCom);
		this.add(panelLeft);
		this.add(panelRight);
		this.add(panelBottom);		
		jListLeft.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!jListLeft.getValueIsAdjusting()){
//					int indexSelected = jListLeft.getSelectedIndex();
//					System.out.println("选择的下标："+indexSelected);
					String name = (String)jListLeft.getSelectedValue();
					System.out.println("选择的名字:"+name);
					String information = getRacesInformation(name);
					jTextRight.setText(information);
				}
			}
		});
	}
	
	public String getRacesInformation(String name){
		Report reports = Reports.reports.get(name);
		if(reports == null)
		{
			return "";
		}else{
			Set<Race> races = reports.getRaces();
			Race race = null;
			String information = "";
			int count = 0;
			Iterator it = races.iterator();
			while(it.hasNext()){
				count++;
				race = (Race) it.next();
				information +=  count + ". line: " + race.getLine1() + " and " + race.getLine2()
						+ "\nvariable: " + race.getVariable() + "\nclass: " + race.getPackageClass()
						+ "\ndetail: " + race.getDetail() + "\n";
			}
			if(information.equals(""))
			{
				return "";
			}else{
				return information ;
			}
		}
	}
}
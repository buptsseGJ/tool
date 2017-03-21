package cn.edu.thu.platform.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.parser.ParseXml;

public class AddRaceFrame extends JFrame {
	private JTextField txProgramName = new JTextField();
	private JTextField txLine1 = new JTextField();
	private JTextField txLine2 = new JTextField();
	private JTextField txVariable = new JTextField();
	private JTextField txPackageClass = new JTextField();
	private JTextArea txDetail = new JTextArea();
	private JScrollPane jsp = new JScrollPane(txDetail);
	private JTextField txTime = new JTextField();
	private JLabel lbProgramName = new JLabel("program name:");
	private JLabel lbLine1 = new JLabel("line1:");
	private JLabel lbLine2 = new JLabel("line2:");
	private JLabel lbVariable = new JLabel("variable:");
	private JLabel lbPackageClass = new JLabel("package-class:");
	private JLabel lbDetail = new JLabel("detail:");
	private JButton btConfirm = new JButton("确定");
	private JButton btCancel = new JButton("取消");
	private JButton btSearch = new JButton("查询");
	private DefaultListModel<String> model;
	
	public AddRaceFrame(String programName, DefaultListModel<String> model){
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
//		panel.setBackground(Color.blue);
		panel.setBounds(0, 0, 1000, 800);
		Font font = new Font("宋体", Font.PLAIN, 20); 
		lbProgramName.setFont(font);
		lbLine1.setFont(font);
		lbLine2.setFont(font);
		btSearch.setFont(font);
		lbVariable.setFont(font);
		lbPackageClass.setFont(font);
		lbDetail.setFont(font);
		lbProgramName.setBounds(50, 10, 150, 30);
		lbLine1.setBounds(50, 50, 150, 30);
		lbLine2.setBounds(50, 90, 150, 30);
		btSearch.setBounds(450, 150, 100, 30);
		lbVariable.setBounds(50, 190, 150, 30);
		lbPackageClass.setBounds(50, 230, 150, 30);
		lbDetail.setBounds(50, 270, 150, 30);
		txProgramName.setBounds(220, 10, 550, 30);
		txLine1.setBounds(220, 50, 550, 30);
		txLine2.setBounds(220, 90, 550, 30);
		txVariable.setBounds(220, 190, 550, 30);
		txPackageClass.setBounds(220, 230, 550, 30);
		jsp.setBounds(50, 310, 850, 300);
		btConfirm.setBounds(150, 650, 150, 50);
		btCancel.setBounds(400, 650, 150, 50);
		panel.add(lbProgramName);
		panel.add(lbLine1);
		panel.add(lbLine2);
		panel.add(btSearch);
		panel.add(lbVariable);
		panel.add(lbPackageClass);
		panel.add(lbDetail);
		panel.add(txProgramName);
		panel.add(txLine2);
		panel.add(txLine1);
		panel.add(txVariable);
		panel.add(txPackageClass);
		panel.add(jsp);
		panel.add(btConfirm);
		panel.add(btCancel);
		this.add(panel);
		txProgramName.setText(programName);
		txProgramName.setEditable(false);
		this.model = model;
		btSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txProgramName.getText().equals("")){
					JOptionPane.showMessageDialog(null,"请输入programName的内容");
				}else if(txLine1.getText().equals("")){
					JOptionPane.showMessageDialog(null, "请输入line1的内容");
				}else if(txLine2.getText().equals("")){
					JOptionPane.showMessageDialog(null,"请输入line2的内容");
				}else{
					Report report = Reports.reports.get(txProgramName.getText());
					Set<Race> races = report.getRaces();
					Iterator it = races.iterator();
					Race race = null;
					boolean flag = false;
					txLine1.setEditable(false);
					txLine2.setEditable(false);
					while(it.hasNext()){
						race = (Race) it.next();
						if(race.getLine1().equals(txLine1.getText())&&race.getLine2().equals(txLine2.getText())
								||race.getLine2().equals(txLine1.getText())&&race.getLine1().equals(txLine2.getText())){
							txProgramName.setEditable(false);
							txVariable.setText(race.getVariable());
							txPackageClass.setText(race.getPackageClass());
							txDetail.setText(race.getDetail());
							JOptionPane.showMessageDialog(null, "已找到这个race，您可以进行修改。");
							flag = true;
						}
					}
					if(flag == false){
						JOptionPane.showMessageDialog(null, "当前bug库中不存在此记录，您可以新增race。");
					}
					btSearch.setEnabled(false);
				}
			}			
		});
		btCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Race tempRace = new Race(txLine1.getText(), txLine2.getText(), txVariable.getText(), txPackageClass.getText(), txDetail.getText());
				if(isValidate(tempRace)){
					ParseXml parse = new ParseXml();
					try {
						Document doc = parse.buildDocument("");
						parse.addElement(doc, txProgramName.getText(), tempRace);
						parse.writeDomToXml(doc);
						updateStaticVariables(tempRace,txProgramName.getText());
						JOptionPane.showMessageDialog(null, "增加race修改完成");
						dispose();
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void updateStaticVariables(Race race, String programName){
		Report tempReport = Reports.reports.get(programName);
		Set<Race> tempRaces = tempReport.getRaces();
		Iterator it = tempRaces.iterator();
		String line1 = race.getLine1();
		String line2 = race.getLine2();
		String temp = "";
		temp = line1;
		boolean flag = false;
		if(Integer.parseInt(line1)>Integer.parseInt(line2)){
			line1 = line2;
			line2 = temp;
		}
		while(it.hasNext()){
			Race tempRace = (Race) it.next();
			String currentLine1 = tempRace.getLine1();
			String currentLine2 = tempRace.getLine2();
			temp = currentLine1;
			if(Integer.parseInt(currentLine1)>Integer.parseInt(currentLine2)){
				currentLine1 = currentLine2;
				currentLine2 = temp;
			}
			if(line1==currentLine1&&line2==currentLine2){
				tempRaces.remove(tempRace);
				Race newRace = new Race(currentLine1, currentLine2, race.getVariable(), race.getPackageClass(), race.getDetail());
				tempRaces.add(newRace);
				flag = true;
				break;
			}
		}
		if(flag == false){
			Race newRace = new Race(line1, line2, race.getVariable(), race.getPackageClass(), race.getDetail());
			tempRaces.add(newRace);
		}
		ManageSuitesFrame.getRacesList(programName);
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
}
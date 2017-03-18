package cn.edu.thu.platform.frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddRaceFrame extends JFrame {
	private JTextField txProgramName = new JTextField();
	private JTextField txLine1 = new JTextField();
	private JTextField txLine2 = new JTextField();
	private JTextField txVariable = new JTextField();
	private JTextField txPackageClass = new JTextField();
	private JTextArea txDetail = new JTextArea();
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
	
	public AddRaceFrame(String programName){
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
//		panel.setBackground(Color.blue);
		panel.setBounds(0, 0, 1000, 800);
		Font font = new Font("宋体", Font.PLAIN, 20); 
		lbProgramName.setFont(font);
		lbLine1.setFont(font);
		lbProgramName.setBounds(50, 10, 150, 30);
		lbLine1.setBounds(50, 50, 150, 30);
		panel.add(lbProgramName);
		panel.add(lbLine1);
		this.add(panel);
	}
}

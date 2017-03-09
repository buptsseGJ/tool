package cn.edu.thu.platform.frame;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RoughResultFrame extends JFrame {
	private JTextArea jText = new JTextArea();
	private JButton btSaveResult = new JButton("另存报告");
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("报告已经存放在根目录下的ResultReport.txt下");
	
	public RoughResultFrame(){
		this.setLayout(null);
		panel.setLayout(null);
		panel.setBounds(0, 0, 1500, 1000);
		JScrollPane jsp = new JScrollPane(jText);
		jsp.setBounds(10, 100, 1450, 950);
		jText.setLineWrap(true);
		label.setBounds(20, 20, 500, 40);
		panel.add(jsp);
		panel.add(label);
		this.add(panel);
		generateReport();
	}
	
	public void generateReport(){
		String filePath = "";
		try {
			FileWriter writer = new FileWriter(filePath, true);
			writer.write("\n----------------------------------------\n----------------------------------------\n");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			writer.write("\t" + df.format(new Date()));
			writer.write("\n----------------------------------------\n----------------------------------------\n");
			writeResult(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeResult(FileWriter writer){
		int count = 0;
		
	}
}

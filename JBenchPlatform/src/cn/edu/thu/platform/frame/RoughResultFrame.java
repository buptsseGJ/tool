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

import cn.edu.thu.platform.comparison.Comparison;
import cn.edu.thu.platform.comparison.ComparisonResult;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.match.Match;
import cn.edu.thu.platform.match.MatchInterface;

public class RoughResultFrame extends JFrame {
	private JTextArea jText = new JTextArea();
	private JButton btSaveResult = new JButton("另存报告");
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("报告已经存放在根目录下的ResultReport.txt下");
	private String totalMessage = "";
	public RoughResultFrame() {

		// 根据参数ComparisonResult.tool 由Match类返回 对应需要实例化哪一个类
		MatchInterface match = Match.getMatch(ComparisonResult.tool);
		// 调用对应方法，提取文件中所有的 用例名 行号对 等信息
		match.matchFile();
		totalMessage = "";
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

	public void generateReport() {
		String filePath = "../result1.txt";
		try {
			FileWriter writer = new FileWriter(filePath, true);
			writer.write("\n----------------------------------------\n----------------------------------------\n");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			writer.write("\t" + df.format(new Date()));
			writer.write("\n----------------------------------------\n----------------------------------------\n");
			writeResult(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeResult(FileWriter writer) {
		Comparison com = new Comparison();
		int numOfNames = Reports.programNames.size();
		Report originalReport;
		Report newReport;
		for(int i=0;i < numOfNames; i++){
			String name = Reports.programNames.get(i);
			originalReport = Reports.compareReports.get(name);

			newReport = Reports.userReports.get(name);
			String message = com.compare(originalReport, newReport);
			totalMessage += (i+1) + ". " + name + message +"\n\n";
		}
		jText.setText(totalMessage);
	}
}

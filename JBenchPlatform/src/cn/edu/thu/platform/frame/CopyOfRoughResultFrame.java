package cn.edu.thu.platform.frame;

import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.edu.thu.platform.comparison.Comparison;
import cn.edu.thu.platform.comparison.ComparisonResult;
import cn.edu.thu.platform.entity.Race;
import cn.edu.thu.platform.entity.Report;
import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.entity.Result;
import cn.edu.thu.platform.match.Match;
import cn.edu.thu.platform.match.MatchInterface;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.ComponentOrientation;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Point;

public class CopyOfRoughResultFrame extends JFrame {
	private String totalMessage = "\n\n      Detail introdution:\n";
	private JTextPane txtpnTimeTool = new JTextPane();
	private JTable table;
	JTextPane txtpane = new JTextPane();
	Object[][] data;
	String[] columnNames = {"No.","NAME","BENCHMARKS","SCRIPTFILE","TOTAL","FOUNDED","MISSING","EXTRA","PRECISION","FNR","FPR"};

	public CopyOfRoughResultFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setFont(new Font("Century Gothic", Font.PLAIN, 22));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new GridLayout(3, 1, 1, 5));
		

		// 根据参数ComparisonResult.tool 由Match类返回 对应需要实例化哪一个类
		MatchInterface match = Match.getMatch(ComparisonResult.tool);
		// 调用对应方法，提取文件中所有的 用例名 行号对 等信息
		match.matchFile();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setSize(new Dimension(1000, 300));
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		//title
		JLabel lblNewLabel = new JLabel("Dynamic Testing Report");
		lblNewLabel.setBounds(0, 0, 1061, 131);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
		panel_1.add(lblNewLabel);

		//time&tool
		txtpnTimeTool.setText("Time  Tool");
		txtpnTimeTool.setPreferredSize(new Dimension(6, 10));
		txtpnTimeTool.setMargin(new Insets(5, 10, 0, 10));
		txtpnTimeTool.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		txtpnTimeTool.setEditable(false);
		txtpnTimeTool.setBounds(618, 117, 461, 53);
		panel_1.add(txtpnTimeTool);
		
		//line1
		JTextPane txtpaneLine1 = new JTextPane();
		txtpaneLine1.setBounds(0, 165, 1061, 100);
		txtpaneLine1.setPreferredSize(new Dimension(6, 10));
		txtpaneLine1.setMargin(new Insets(5, 10, 0, 10));
		txtpaneLine1.setEditable(false);
		txtpaneLine1.setText("       将本次测试中各测试用例的测试结果与benchmarks进行对比，得到的对比结果如下表所示。详细的race信息可见位于根目录下的测试报告——resultReport.txt");
		txtpaneLine1.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		panel_1.add(txtpaneLine1);

		generateReport();
		
//		data = {{"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"},
//						   {"11","11","11","11","11","11","11","11","11","11"}};
		
		//table
		JPanel panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.setBorder(null);
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);
		table = new JTable(data, columnNames);
		table.setPreferredSize(new Dimension(825, 400));
		table.setPreferredScrollableViewportSize(new Dimension(450, 200));
		table.setFillsViewportHeight(true);
		table.setName("测试用例运行结果统计表");
		table.setColumnSelectionAllowed(true);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		table.setGridColor(new Color(128, 128, 128));
		table.setEnabled(false);
		table.setBorder(null);
		table.setAutoCreateColumnsFromModel(false);
		table.setModel(new DefaultTableModel(data,columnNames) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, true, true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(57);
		table.getColumnModel().getColumn(1).setPreferredWidth(142);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(137);
		table.setRowHeight(30);
		table.setRequestFocusEnabled(false);
		table.setVerifyInputWhenFocusTarget(false);
		table.setUpdateSelectionOnSort(false);
		table.setRowSelectionAllowed(false);
		Dimension size = table.getTableHeader().getPreferredSize();
		size.height = 50;//设置新的表头高度
		table.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(size);
		panel.setLayout(new BorderLayout(0, 0));
		table.setBackground(new Color(240, 248, 255));
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setWheelScrollingEnabled(false);
		scrollPaneTable.setViewportBorder(null);
		scrollPaneTable.setMinimumSize(new Dimension(32, 13));
		scrollPaneTable.setAutoscrolls(true);
		scrollPaneTable.setFocusable(false);
		scrollPaneTable.setFocusTraversalKeysEnabled(false);
		scrollPaneTable.setEnabled(false);
		scrollPaneTable.setBorder(new LineBorder(new Color(128, 128, 128)));
		scrollPaneTable.setBackground(new Color(255, 255, 255));
		panel.add(scrollPaneTable, BorderLayout.CENTER);
		//table.setBounds(488, 375, 200, 100);
		
		//line2
		txtpane.setMaximumSize(new Dimension(1000, 500));
		txtpane.setText(totalMessage);
		txtpane.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		txtpane.setEditable(false);
		getContentPane().add(txtpane);		
	}

	public void generateReport() {
		String filePath = "../resultReport.csv";
		try {
			FileWriter writer = new FileWriter(filePath, true);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			writer.write("DATE:,"+df.format(new Date())+"\n");
			writer.write("TOOL:,"+ComparisonResult.tool+"\n");
			
			txtpnTimeTool.setText("DATE : "+df.format(new Date())+"    TOOL : "+ComparisonResult.tool);
			
			writeResult(writer);
			
			writer.write("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeResult(FileWriter writer) {
		//表头   误报率 FPR  漏报率  FNR
		String csvTotalStr = ",,,Statistic description:\n";
		String csvDetailStr = ",,,Races details:\n";
		try {
			writer.write(",,No.,USECASE NAME,IS IN BENCHMARKS,IS IN SCRIPTFILE,TOTAL RACES,FOUNDED RACES,MISSING RACES,EXTRA RACES,PRECISION,FNR,FPR,\n");
			//在benchmarks中，且再执行脚本中
			
			
			//在benchmarks中，但不在执行脚本中
			
			Comparison com = new Comparison();
			int numOfNames = Reports.userNames.size();
			Report originalReport;
			Report newReport;
			int count = 1;
			
			data= new Object[numOfNames+Reports.wrongNames.size()][11];
			
			for(int i = 0;i < numOfNames; i++){
				String name = Reports.programNames.get(i);
				
				originalReport = Reports.compareReports.get(name);
//				"中的race总数是" + originalLength + "个,"
//						+ "该工具成功找到了" + rightMatch + "个,遗漏了"
//						+ (originalLength - rightMatch) + "个,并且额外找出了" + falseMatch
//						+ "个。因此正确率是" + rightMatch / originalLength * 100 + "%,漏报率是" + (originalLength - rightMatch)
//						/ originalLength * 100 + "%,误报率是" + falseMatch / originalLength * 100
//						+ "%。\n";
				
				newReport = Reports.userReports.get(name);
				String message = com.compare(name, originalReport, newReport);
				Result curCaseResults = ComparisonResult.resultsMap.get(name);
				int totalRaceNum = curCaseResults.getTotalRaace();
				int missRaceNum = curCaseResults.getMissedRace();
				int rightRaceNum = curCaseResults.getMathchedRace();
				int extraRaceNum = curCaseResults.getAdditianlRace();
				writer.write(",,"+count+","+curCaseResults.getProgramName()+",TRUE,TRUE,"+totalRaceNum+","+rightRaceNum+","+
							missRaceNum+","+extraRaceNum+","+(rightRaceNum/totalRaceNum*100)+"%,"+((totalRaceNum-rightRaceNum)/totalRaceNum*100)+
							"%,误报率是"+(missRaceNum/totalRaceNum*100)+"%,\n");
				totalMessage += "	"+count + ". " + name +"中的race总数是"+totalRaceNum+",该工具成功找到了"+rightRaceNum+"个,遗漏了"+
						missRaceNum+"个,并且额外找出了"+extraRaceNum+"个;\n	      因此正确率是"+(rightRaceNum/totalRaceNum*100)+"%,漏报率是"+((totalRaceNum-rightRaceNum)/totalRaceNum*100)+
						"%,"+(missRaceNum/totalRaceNum*100)+"%;\n";
				csvTotalStr = csvTotalStr+",,"+count + ","+name+",total "+totalRaceNum+" races;,"+rightRaceNum+" right race;,"+missRaceNum+" missing race;,"+extraRaceNum+" extra race;,precision is "+(rightRaceNum/totalRaceNum*100)+"%;,FNR is "+((totalRaceNum-rightRaceNum)/totalRaceNum*100)+"%;,FPR is"+(missRaceNum/totalRaceNum*100)+"%;,\n";
				data[count-1][0]= count;
				data[count-1][1]= name;
				data[count-1][2]= "TRUE";
				data[count-1][3]= "TRUE";
				data[count-1][4]= totalRaceNum;
				data[count-1][5]= rightRaceNum;
				data[count-1][6]= missRaceNum;
				data[count-1][7]= extraRaceNum;
				data[count-1][8]= (rightRaceNum/totalRaceNum*100)+"%";
				data[count-1][9]= ((totalRaceNum-rightRaceNum)/totalRaceNum*100)+"%";
				data[count-1][10]= (missRaceNum/totalRaceNum*100)+"%";
				count++;
				Set<Race> rightRaces = ComparisonResult.findRace.get(name).getCompareRaces();
				Iterator<Race> rightIt = rightRaces.iterator();
				int raceNum = 1;
				while(rightIt.hasNext()) {
					Race r = (Race) rightIt.next();
					if(raceNum==1) {
						csvDetailStr = csvDetailStr +",,,NAME,No.,STATE,CLASS,Line1,Line2,VARIABLE,DETAILs,\n";
						csvDetailStr = csvDetailStr +",,,"+name+","+raceNum + ",correctly found," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					}else
						csvDetailStr = csvDetailStr +",,,,"+raceNum + ",correctly found," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					raceNum++;
				}
				Set<Race> missRaces = ((Report)ComparisonResult.missRace.get(name)).getCompareRaces();
				Iterator<Race> missIt = missRaces.iterator();
				while(missIt.hasNext()) {
					Race r = (Race) missIt.next();
					if(raceNum==1) {
						csvDetailStr = csvDetailStr +",,,NAME,No.,STATE,CLASS,Line1,Line2,VARIABLE,DETAILs,\n";
						csvDetailStr = csvDetailStr +",,,"+name+","+raceNum + ",missing," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					}else
						csvDetailStr = csvDetailStr +",,,,"+raceNum + ",missing," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					raceNum++;
				}
				Set<Race> extraRaces = ComparisonResult.additianalRace.get(name).getCompareRaces();
				Iterator<Race> extraIt = extraRaces.iterator();
				while(extraIt.hasNext()) {
					Race r = (Race) extraIt.next();
					if(raceNum==1) {
						csvDetailStr = csvDetailStr +",,,NAME,No.,STATE,CLASS,Line1,Line2,VARIABLE,DETAILs,\n";
						csvDetailStr = csvDetailStr +",,,"+name+","+raceNum + ",additional," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					}else
						csvDetailStr = csvDetailStr +",,,,"+raceNum + ",additional," +r.getPackageClass()+ "," +r.getLine1()+ "," +r.getLine2()+ "," +r.getVariable()+ "," +r.getDetail()+",\n";
					raceNum++;
				}
			}

			//不在benchmarks中，但在执行脚本中
			for(int i = 0; i < Reports.wrongNames.size(); i++){
				totalMessage += "	"+count + ". 测试集中没有名为[" + Reports.wrongNames.get(i) + "]的程序;\n";
				writer.write(",,"+count+","+Reports.wrongNames.get(i)+",FALSE,TRUE,"+"--"+","+"--"+","+
						"--"+","+"--"+","+"--"+","+"--"+","+"--"+",\n");
				csvTotalStr = csvTotalStr+",,"+count + ". ,"+Reports.wrongNames.get(i)+",is not in benchmarks;\n";
				data[count-1][0]= count;
				data[count-1][1]= Reports.wrongNames.get(i);
				data[count-1][2]= "FALSE";
				data[count-1][3]= "TRUE";
				data[count-1][4]= "--";
				data[count-1][5]= "--";
				data[count-1][6]= "--";
				data[count-1][7]= "--";
				data[count-1][8]= "--";
				data[count-1][9]= "--";
				data[count-1][10]= "--";
				count++;
			}
			//漏掉的程序名信息输出
			List<String> subtractionList = new ArrayList<String>();
			subtractionList.addAll(Reports.programNames);
			subtractionList.removeAll(Reports.userNames);
			if(!subtractionList.isEmpty()){
				int innerCount = 1;
				totalMessage += count + ". 测试集中的以下程序还没有被测试：\n";
				for(int i = 0; i < subtractionList.size(); i++){
					totalMessage += "	" + count + ". "+innerCount + "位于测试集中，且还没有被测试;\n";
					writer.write(",,"+count+","+subtractionList.get(i)+",TRUE,FALSE,"+","+"--"+","+"--"+","+
							"--"+","+"--"+","+"--"+","+"--"+","+"--"+",\n");
					csvTotalStr =csvTotalStr+",,"+count + ". ,"+subtractionList.get(i)+",in scriptfile,not in benchmarks;\n";
					data[count-1][0]= count;
					data[count-1][1]= subtractionList.get(i);
					data[count-1][2]= "TRUE";
					data[count-1][3]= "FALSE";
					data[count-1][4]= "--";
					data[count-1][5]= "--";
					data[count-1][6]= "--";
					data[count-1][7]= "--";
					data[count-1][8]= "--";
					data[count-1][9]= "--";
					data[count-1][10]= "--";
					innerCount ++;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {
			writer.write(csvTotalStr);
			writer.write(csvDetailStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		txtpane.setText(totalMessage);
	}
}

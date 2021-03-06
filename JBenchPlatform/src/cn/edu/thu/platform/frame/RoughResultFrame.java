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
import javax.swing.table.TableCellRenderer;

import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.GridLayout;
import java.awt.Scrollbar;

import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;

public class RoughResultFrame extends JFrame {
	private String totalMessage = "      Detail introdution:\n";
	private JTextPane txtpnTimeTool = new JTextPane();
	private JTable table;
	JTextPane txtpane = new JTextPane();
	Object[][] data;
	String[] columnNames = {"No.","NAME","BENCHMARKS","SCRIPTFILE","TOTAL","FOUNDED","MISSING","EXTRA","PRECISION","FNR","FPR"};

	public RoughResultFrame() {
		getContentPane().setSize(new Dimension(1200, 1000));
		setSize(new Dimension(1200, 1000));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setFont(new Font("Century Gothic", Font.PLAIN, 22));
		getContentPane().setBackground(Color.WHITE);

		//add the basic JPanel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(1178, 1000));
		scrollPane.setPreferredSize(new Dimension(1178, 1000));
		scrollPane.setBounds(0, 0, 1178, 1000);
		//scrollPane.setSize(new Dimension(0, 4000));
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(0, 0, 1178, 1000));
		panel.setSize(new Dimension(1178, 1000));
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		getContentPane().add(scrollPane);


		// 根据参数ComparisonResult.tool 由Match类返回 对应需要实例化哪一个类
		MatchInterface match = Match.getMatch(ComparisonResult.tool);
		// 调用对应方法，提取文件中所有的 用例名 行号对 等信息
		match.matchFile();
		getContentPane().setLayout(null);
		
		JScrollPane txtscrollPane = new JScrollPane();
		txtpane.setText(totalMessage);
		txtpane.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
//		txtpane.setPreferredSize(new Dimension(1170,txtpane.getPreferredSize().height));
		txtpane.setEditable(false);
		txtscrollPane.setViewportView(txtpane);
		txtscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtscrollPane.setSize(1150, txtpane.getPreferredSize().height);
		txtscrollPane.setLocation(1170, 0);
		panel.add(txtscrollPane);
		
		generateReport();

		//title
		JLabel lblNewLabel = new JLabel("Dynamic Testing Report");
		lblNewLabel.setBounds(0, 0, 1061, 131);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
		panel.add(lblNewLabel);

		//time&tool
		txtpnTimeTool.setPreferredSize(new Dimension(6, 10));
		txtpnTimeTool.setMargin(new Insets(5, 10, 0, 10));
		txtpnTimeTool.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		txtpnTimeTool.setEditable(false);
		txtpnTimeTool.setBounds(559, 126, 461, 37);
		panel.add(txtpnTimeTool);

		//line1
		JTextPane txtpaneLine1 = new JTextPane();
		txtpaneLine1.setBounds(50, 160, 1090, 68);
		panel.add(txtpaneLine1);
		txtpaneLine1.setPreferredSize(new Dimension(6, 10));
		txtpaneLine1.setMargin(new Insets(5, 10, 0, 10));
		txtpaneLine1.setEditable(false);
		txtpaneLine1.setText("       将本次测试中各测试用例的测试结果与benchmarks进行对比，得到的对比结果如下表所示。详细的race信息可见位于根目录下的测试报告——resultReport.txt");
		txtpaneLine1.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		
		table = new JTable(data, columnNames) {
			@Override
			public JTableHeader getTableHeader() {
				// TODO Auto-generated method stub
				JTableHeader j =  super.getTableHeader();
				
				j.setVisible(true);
				
				return j;
			}
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				// TODO Auto-generated method stub
				Component c =  super.prepareRenderer(renderer, row, column);
				
				
				
				return c;
			}
		};
		
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
		size.height = 50;
		table.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(size);
		table.setBackground(new Color(240, 248, 255));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(table);
		scrollPane_1.setBackground(new Color(255, 255, 255));
		scrollPane_1.setBorder(new LineBorder(Color.DARK_GRAY));
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(50, 243, 825, 400);
		scrollPane_1.setSize(new Dimension(1070,table.getRowCount()*30+50));
		panel.add(scrollPane_1);

		//line2
		txtscrollPane.setBorder(null);
		txtscrollPane.setSize(1100, txtpane.getPreferredSize().height);
		txtscrollPane.setLocation(30, table.getRowCount()*30+320);
		txtscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		txtpane.setPreferredSize(new Dimension(1170,txtpane.getPreferredSize().height));
//		txtpane.setBounds(70, scrollPane_1.getY()+table.getRowCount()*30+50, 1170, txtpane.getPreferredSize().height+30);
		
		int height = txtpane.getPreferredSize().height+scrollPane_1.getHeight()+300>997? txtpane.getPreferredSize().height+scrollPane_1.getHeight()+300:997;
		panel.setPreferredSize(new Dimension(1178, height));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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

			data= new Object[(Reports.wrongNames.size()+Reports.programNames.size())][11];

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
						"%,误报率是"+(missRaceNum/totalRaceNum*100)+"%;\n";
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
				for(int i = 0; i < subtractionList.size(); i++){
					totalMessage += "	" + count + ". "+subtractionList.get(i) + "位于测试集中，且还没有被测试;\n";
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
					count++;
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
		txtpane.setPreferredSize(new Dimension(1170,txtpane.getPreferredSize().height));
	}
}

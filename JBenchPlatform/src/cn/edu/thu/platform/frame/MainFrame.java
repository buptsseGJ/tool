package cn.edu.thu.platform.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.w3c.dom.Document;

import cn.edu.thu.platform.entity.Reports;
import cn.edu.thu.platform.parser.DomToEntity;
import cn.edu.thu.platform.parser.ParseXml;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Panel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Cursor;

/**
 * 主界面 主要功能： 1）读取benchamrk readSuites 2）管理benchmark manageSuites 3）运行程序
 * runProgram 4）程序脚本 scriptFile
 * 
 */
public class MainFrame extends JFrame {

	private JButton readSuites = new JButton("Import");
	private JButton manageSuites = new JButton("Manage");
	private JButton runProgram = new JButton("By program");
	private JButton scriptFile = new JButton("By scriptFile");
	private final JPanel information = new JPanel();
	private final JLabel lblRunUsecases = new JLabel(" Run usecases");
	File file = null;
	public TextArea textArea = new TextArea("",50, 1, TextArea.SCROLLBARS_BOTH);
	public String textAreaInfo = "";

	public MainFrame() {
		getContentPane().setFont(new Font("Century Gothic", Font.PLAIN, 22));
		JPanel p1 = new ButtonPanel();
		p1.setLayout(null);
		p1.setBounds(0, 0, 428, 533);
		getContentPane().setLayout(null);
		getContentPane().add(p1);

		JLabel label = new JLabel("Benchmarks");
		label.setOpaque(true);
		label.setBounds(24, 29, 105, 21);
		p1.add(label);
		label.setEnabled(false);
		label.setBackground(UIManager.getColor("menu"));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 17));

		JPanel benchmarks = new JPanel();
		benchmarks.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		benchmarks.setBorder(new TitledBorder(new LineBorder(new Color(211, 211, 211)), "Benchmarks", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));		
		benchmarks.setBounds(15, 29, 161, 153);
		p1.add(benchmarks);
		benchmarks.setLayout(null);
		manageSuites.setBounds(15, 92, 127, 37);
		manageSuites.setFocusable(false);
		benchmarks.add(manageSuites);
		manageSuites.setForeground(Color.DARK_GRAY);
		manageSuites.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		manageSuites.setEnabled(false);

		readSuites.setHorizontalAlignment(SwingConstants.LEFT);
		readSuites.setBounds(15, 40, 127, 37);
		benchmarks.add(readSuites);
		readSuites.setBackground(Color.WHITE);
		readSuites.setForeground(Color.DARK_GRAY);
		readSuites.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		readSuites.setFocusable(false);
		lblRunUsecases.setOpaque(true);
		lblRunUsecases.setEnabled(false);
		lblRunUsecases.setBounds(211, 29, 116, 21);
		p1.add(lblRunUsecases);
		lblRunUsecases.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblRunUsecases.setBackground(SystemColor.menu);

		JPanel programs = new JPanel();
		programs.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		programs.setBounds(204, 39, 193, 140);
		p1.add(programs);
		programs.setLayout(null);
		runProgram.setBounds(15, 28, 159, 37);
		programs.add(runProgram);
		runProgram.setFocusable(false);
		runProgram.setEnabled(false);
		scriptFile.setFocusable(false);
		scriptFile.setEnabled(false);
		runProgram.setForeground(Color.DARK_GRAY);
		runProgram.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		scriptFile.setHorizontalAlignment(SwingConstants.LEFT);
		scriptFile.setBounds(15, 82, 159, 37);
		programs.add(scriptFile);
		scriptFile.setForeground(Color.DARK_GRAY);
		scriptFile.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		programs.setBorder(new TitledBorder(new LineBorder(new Color(211, 211, 211)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		information.setBorder(new LineBorder(SystemColor.scrollbar));
		information.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		information.setForeground(Color.DARK_GRAY);
		information.setBackground(SystemColor.inactiveCaptionBorder);
		information.setBounds(10, 219, 396, 304);

		p1.add(information);
		information.setLayout(null);
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		textArea.setBackground(SystemColor.textHighlightText);
		textArea.setEditable(false);
		textArea.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		textArea.setBounds(3, 3, 390, 300);
		information.add(textArea);

		JLabel lblNewLabel = new JLabel("  Running Information :");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblNewLabel.setBounds(15, 198, 231, 21);
		p1.add(lblNewLabel);

		// 管理benchmark 监听函数
		manageSuites.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageBenchmarkFrame mbf = new ManageBenchmarkFrame();
				System.out.println("开始管理benchmark");
				mbf.setSize(1160, 1000);
				mbf.setBounds(750, 150,1160, 1000);
				mbf.setVisible(true);
				mbf.treetable.removeAll();
				mbf.treetable.updateUI();
				mbf.setTitle("管理");
				//				ManageBenchmarkFrame mbf = new ManageBenchmarkFrame();

				//				ManageSuitesFrame msf = new ManageSuitesFrame();				
				//				System.out.println("开始管理benchmark");
				//				msf.setSize(1000, 1000);
				//				msf.setLocationRelativeTo(null);
				//				msf.setVisible(true);
				//				msf.setTitle("管理");
			}
		});
		// 程序脚本 监听函数
		scriptFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 弹出 实现 程序脚本 的新窗口
				SelectScriptFrame ssf = new SelectScriptFrame();
				System.out.println("开始脚本操作");
				ssf.setBounds(750, 150,1160, 1000);
				ssf.setTextArea(textArea);
				//ssf.setLocationRelativeTo(null);
				ssf.setVisible(true);
				ssf.setTitle("脚本");
			}
		});
		// 运行程序 监听函数
		runProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("开始运行程序");
				// String[] command = {"java",
				// "E:\\courseResource\\programResearch\\benchmark相关\\benchmarks汇总\\calfuzzer_calfuzzer\\CalFuzzer_TestRace1\\bin\\benchmarks\\testcases\\TestRace1"
				// }; // Test1是一个class文件，里面有一个main方法
				try {
					String command = "java -cp E:\\courseResource\\programResearch\\benchmark相关\\benchmarks汇总\\calfuzzer_calfuzzer\\CalFuzzer_TestRace1\\bin benchmarks.testcases.TestRace1";
					Process proc = Runtime.getRuntime().exec(command);
					BufferedInputStream in = new BufferedInputStream(proc
							.getInputStream());
					BufferedReader inBr = new BufferedReader(
							new InputStreamReader(in));
					String lineStr;
					while ((lineStr = inBr.readLine()) != null)
						// 获得命令执行后在控制台的输出信息
						System.out.println(lineStr);// 打印输出信息
					// 检查命令是否执行失败。
					if (proc.waitFor() != 0) {
						if (proc.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
							System.err.println("命令执行失败!");
					}
					// inBr.close();
					// in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		// 读benchmark 监听函数
		readSuites.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("开始文件选择");
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				File rootFile = new File(System.getProperty("user.dir").replace('\\', '/')+"/file");
				jfc.setCurrentDirectory(rootFile);
				jfc.showDialog(new JLabel(), "选择");
				file = jfc.getSelectedFile();
				if (file != null) {
					textAreaInfo="";
					textArea.setText(textAreaInfo);
					//							new Thread() {
					//								@Override
					//								public void run() {
					String fileAbsolutePath = file.getAbsolutePath();
					ParseXml parser = new ParseXml();
					Document validationResult = parser.validateXml(fileAbsolutePath);
					if (validationResult != null) {
						textAreaInfo=textAreaInfo+"\nxml文件格式验证通过！\n";
						textArea.setText(textAreaInfo);

						Reports.removeAllBenchmakrs();
						DomToEntity convert = new DomToEntity();
						textAreaInfo = convert.startDom(validationResult,textAreaInfo,textArea);										

						textAreaInfo=textAreaInfo+"\nbenchmarks读取完毕！\n";
						textArea.setText(textAreaInfo);
						textArea.setCaretPosition(textArea.getText().length());

						JOptionPane.showMessageDialog(getContentPane(), "benchmark解析完毕");
						
						manageSuites.setEnabled(true);
						runProgram.setEnabled(true);
						scriptFile.setEnabled(true);
						
					} else {
						System.out.println("错误");
						textAreaInfo=textAreaInfo+"\n\nbenchmarks错误！\n";
						textArea.setText(textAreaInfo);
						textArea.setCaretPosition(textArea.getText().length());
					}
					//								}
					//							}.start();
				}
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    //  设定界面观感
		JFrame mainFrame = new MainFrame();
		mainFrame.setSize(439, 589);
		mainFrame.setTitle("测试平台");
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setBounds(300, 400, 439, 589);
		// frame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	static class ButtonPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}
}

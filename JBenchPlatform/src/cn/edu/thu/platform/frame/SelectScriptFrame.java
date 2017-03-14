package cn.edu.thu.platform.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.thu.platform.comparison.ComparisonResult;
import cn.edu.thu.platform.script.Script;

/**
 * 主界面点击 “程序脚本”，弹出本窗口，实现选择脚本文件、运行脚本、选择并比较结果、查看样例文件等功能
 */
public class SelectScriptFrame extends JFrame implements ChangeListener{
	public JProgressBar progress = new JProgressBar();
	private JTextArea jText = new JTextArea();
	private JButton runScript = new JButton("运行脚本");
	private JButton btCompare = new JButton("比较结果");
	private JButton btChooseResult = new JButton("选择比较文件");
	private JButton btExample = new JButton("查看样例文件");
	private JPanel anotherPanel = new JPanel();
	private BufferedReader readStdout = null;  
    private BufferedReader readStderr = null;  
    private StringBuffer mStringBuffer = new StringBuffer();
    private String tmp1 = null;  
    private String tmp2 = null;  
    // 回调用到的接口  
//    private RealtimeProcessInterface mInterface = null;  
	// private JFileChooser chooseResult = new JFileChooser();
	private JRadioButton cal, rv, date, other;
	private String fileAbsolutePath = "";

	public SelectScriptFrame() {
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setLocation(0, 0);
		panel.setLayout(null);
		panel.setSize(1139, 948);
		panel.setBackground(new Color(245, 245, 245));
		progress.setVisible(true);
		progress.setMinimum(0);
        progress.setStringPainted(true);      // 描绘文字			        
        progress.setBackground(Color.green); // 设置背景色
        progress.setBounds(0, 0, 500, 20);
        panel.add(progress);
        
//        progress.addChangeListener(l);
		
		// 选择文件 提示文本
		JLabel label = new JLabel("请选择一份脚本文件:");
		label.setBounds(378, 34, 231, 55);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// label.setLocation(50, 50);
		label.setBackground(Color.GRAY);
		panel.add(label);

		// 脚本文件选择button
		JButton chooseFile = new JButton("选择脚本");
		chooseFile.setBounds(600, 38, 150, 47);
		chooseFile.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		panel.add(chooseFile);
		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择脚本");
				jfc.setBounds(750, 50, 100, 50);
				File file = jfc.getSelectedFile();
				if (file != null) {
					fileAbsolutePath = file.getAbsolutePath();
					System.out.println("脚本路径：" + file.getAbsolutePath());
					String commands = readFileByLines(fileAbsolutePath);
					jText.setText(commands);
					if (!(jText.getText().equals("") || jText == null)) {
						runScript.setEnabled(true);
					}
				}
			}
		});

		// 文本信息显示框
		jText.setBounds(67, 102, 1002, 648);
		JScrollPane jsp = new JScrollPane(jText);
		jsp.setBounds(67, 100, 1002, 610);
		panel.add(jsp);
		jText.setFont(new Font("Century Gothic", Font.PLAIN, 23));
		jText.setEditable(false);
		if (jText.getText().equals("") || jText == null) {
			runScript.setEnabled(false);
		}

		// 运行脚本 button
		runScript.setBounds(97, 801, 150, 47);
		runScript.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		panel.add(runScript);
		runScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(System.getProperty("user.dir"));
				String writePosition = System.getProperty("user.dir").replace('\\', '/');
				int position = writePosition.lastIndexOf('/');
				writePosition = writePosition.substring(0, position);
				System.out.println("writePosition:" + writePosition);
				String command = "";
				// runCommands(fileAbsolutePath,writePosition);
				// runCommands("E:\\courseResource\\programResearch\\tool\\commands.bat",writePosition);
				Thread th = new Thread(){
					public void run(){
						progress.setValue(0);
						progress.setVisible(true);
						progress.setMaximum(Script.scripts.size());
					}
				};
				th.start();
				try {
					th.join();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				FileWriter writer1;
				try {
					writer1 = new FileWriter(writePosition + "/result.txt", false);
					for (int i = 0; i < Script.scripts.size(); i++) {
						String temp = Script.scripts.get(i);
						String tempFile = writePosition + "/tempFile.bat";
						FileWriter writer = new FileWriter(tempFile, false);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						// writer.write("\necho \"-->start " + "on " + df.format(new Date()) + "<--\"\n");
						writer.write(deleteName(temp));
						// writer.write("\n echo \"-->end<--\"\n");
						writer.close();
						runCommands(tempFile, writePosition, temp,getName(temp),writer1);
					}
					writer1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "执行完毕");
				anotherPanel.setVisible(true);
			}
		});

		// 运行效果比较 button组，JradioButton组 参数、样式设置与添加
		anotherPanel.setBackground(new Color(245, 245, 245));
		anotherPanel.setBorder(new TitledBorder(new LineBorder(new Color(211,211, 211), 3, true), " \u8FD0\u884C\u6548\u679C\u6BD4\u8F83 ",	TitledBorder.LEADING, TitledBorder.TOP, null,new Color(0, 0, 0)));
		anotherPanel.setLayout(null);
		anotherPanel.setSize(100, 600);
		anotherPanel.setBounds(282, 747, 758, 161);
		anotherPanel.add(cal = new JRadioButton("CalFuzzer", true));
		cal.setBackground(new Color(245, 245, 245));
		cal.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		anotherPanel.add(rv = new JRadioButton("Rv-Predict"));
		rv.setBackground(new Color(245, 245, 245));
		rv.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		anotherPanel.add(date = new JRadioButton("DATE"));
		date.setBackground(new Color(245, 245, 245));
		date.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		anotherPanel.add(other = new JRadioButton("other tool"));
		other.setBackground(new Color(245, 245, 245));
		other.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		btCompare.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btCompare.setBounds(67, 90, 133, 45);
		anotherPanel.add(btCompare);
		btExample.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btExample.setBounds(522, 90, 167, 45);
		btExample.setEnabled(false);
		anotherPanel.add(btExample);
		btChooseResult.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		// chooseResult.setFileSelectionMode(JFileChooser.FILES_ONLY );
		// chooseResult.showDialog(new JLabel(), "选择结果文件");
		// chooseResult.setBounds(750, 50, 100, 50);
		btChooseResult.setEnabled(false);
		btChooseResult.setBounds(267, 90, 188, 45);
		anotherPanel.add(btChooseResult);
		cal.setBounds(41, 31, 138, 37);
		rv.setBounds(221, 31, 155, 37);
		date.setBounds(418, 31, 98, 37);
		other.setBounds(558, 31, 155, 37);
		ButtonGroup group = new ButtonGroup();
		group.add(cal);
		group.add(rv);
		group.add(date);
		group.add(other);
		panel.add(anotherPanel);
		getContentPane().add(panel);

		// 比较运行效果的各个JRadioButton的响应监听，实现单选框效果
		cal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "CalFuzzer";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}

		});
		rv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "Rv-Predict";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}
		});
		date.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "DATE";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}

		});
		other.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "other";
				btCompare.setEnabled(false);
				btChooseResult.setEnabled(true);
				btExample.setEnabled(true);
			}

		});

		// 查看样例文件 button 响应
		btExample.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExampleFrame ex = new ExampleFrame();
				System.out.println("开始查看样例");
				ex.setSize(500, 500);
				ex.setLocationRelativeTo(null);
				ex.setVisible(true);
				ex.setTitle("样例");
			}
		});

		// 比较结果 button 响应
		btCompare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoughResultFrame rrf = new RoughResultFrame();;
				System.out.println("开始查看粗糙的结果展示");
				rrf.setSize(1500, 1000);
				rrf.setLocationRelativeTo(null);
				rrf.setVisible(true);
				rrf.setTitle("结果展现");
			}
		});
	}

	public String getName(String command) {
		String name = command;
		int index = command.indexOf(']');
		name = command.substring(1, index);
		return name;
	}

	public String deleteName(String command) {
		int index = command.indexOf(']');
		command = command.substring(index + 1);
		return command;
	}

	// 运行脚本命令 的处理函数
	public void runCommands(String scriptFile, String filePath, String command,String programName,FileWriter writer) {
		Thread progressThread = new Thread(){
			public void run(){
				progress.setValue(progress.getValue()+1);
				System.out.println("progress值："+(progress.getValue()));
//				int value = progress.getValue();
//				progress.setString("zhi:"+(value*1.0)/(Script.scripts.size())*100 + "%");
//				System.out.println("百分比："+ (value*1.0)/(Script.scripts.size())*100 + "%");
			}
		};
		progressThread.start();
		try {
			progressThread.join();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Process proc = Runtime.getRuntime().exec(scriptFile);

			//Rv-Predict的输出走的是错误输出流，Calfuzzer的输出走的是标准输出流
//			BufferedInputStream in = null;
//			if(ComparisonResult.tool.equals("Rv-Predict")) {
//				in = new BufferedInputStream(proc.getErrorStream());
//			}else {
//				in = new BufferedInputStream(proc.getInputStream());
//			}
//
//			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			 // 获取标准输出  
	        readStdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
	        // 获取错误输出  
	        readStderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));  
	        mStringBuffer.replace(0, mStringBuffer.length(), "");
	        Thread execThread = new Thread(){  
	            public void run(){  
	                try {  
	                    // 逐行读取  
	                    while((tmp1 = readStdout.readLine()) != null || (tmp2 = readStderr.readLine()) != null){  
	                    if(tmp1 != null){  
	                        mStringBuffer.append(tmp1 + "\n");  
	                        // 回调接口方法  
//	                        mInterface.onNewStdoutListener(tmp1);  
	                    }  
	                    if(tmp2 != null){  
	                        mStringBuffer.append(tmp2 + "\n");  
//	                        mInterface.onNewStderrListener(tmp2);  
	                    }  
	                    }  
	                } catch (IOException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	                 
	            }  
	        };  
	        execThread.start();  
	        execThread.join();  
	        execThread.stop();
			String lineStr;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			writer.write("\n>>>>>start command " + command + " about "	+ programName + " on " + df.format(new Date()) + " <<<<<\n");
//			while ((lineStr = inBr.readLine()) != null) {
//				// 获得命令执行后在控制台的输出信息
//				System.out.println(lineStr);// 打印输出信息
//				writer.write(lineStr + "\n");
//			}
			writer.write(mStringBuffer.toString());
			// 检查命令是否执行失败。
			if (proc.waitFor() != 0) {
				if (proc.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
				writer.write("命令执行失败");
			}
			writer.write("\n>>>>>end<<<<<\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String commands = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
				commands += tempString + "\n";
				Script.scripts.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return commands;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==progress){
			int value = progress.getValue();
			progress.setString("zhi:"+(value*1.0)/(Script.scripts.size())*100 + "%");
			System.out.println("zhi:"+(value*1.0)/(Script.scripts.size())*100 + "%");
		}
	}
}

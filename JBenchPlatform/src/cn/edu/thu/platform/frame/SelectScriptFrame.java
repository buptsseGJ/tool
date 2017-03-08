package cn.edu.thu.platform.frame;

import java.awt.Color;
import java.awt.Dimension;
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
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.edu.thu.platform.comparison.ComparisonResult;
import cn.edu.thu.platform.script.Script;

public class SelectScriptFrame extends JFrame {
	private JTextArea jText = new JTextArea();	
	private JButton runScript = new JButton("运行脚本");
	private JButton btCompare = new JButton("比较结果");
	private JButton btChooseResult = new JButton("选择比较文件");
	private JButton btExample = new JButton("查看样例文件");
	JPanel anotherPanel = new JPanel();
//	private JFileChooser chooseResult = new JFileChooser();
	private JRadioButton cal,rv,date,other;
	private String fileAbsolutePath ="";
	public SelectScriptFrame(){
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1000, 1000);
		JLabel label = new JLabel("请选择一份脚本文件:");
		label.setBounds(150, 30, 350, 50);
		label.setFont(new Font("宋体",Font.BOLD,20));
//		label.setLocation(50, 50);
		label.setBackground(Color.GRAY);
		panel.add(label);
		panel.setBackground(Color.green);
		JButton chooseFile = new JButton("选择脚本");
		chooseFile.setBounds(400, 40, 150, 35);
		chooseFile.setFont(new Font("宋体",Font.BOLD,18));
		panel.add(chooseFile);
		jText.setFont(new Font("Arial",Font.BOLD,15));
		jText.setEditable(false);
		JScrollPane jsp = new JScrollPane(jText);
		jsp.setBounds(80, 100, 800, 650);		
		panel.add(jsp);
		runScript.setBounds(50, 880, 100, 40);
		panel.add(runScript);		
		
		anotherPanel.setLayout(null);
		anotherPanel.setSize(100, 600);
		anotherPanel.setBounds(200, 780, 700, 150);
//		anotherPanel.setBackground(Color.BLUE);
		anotherPanel.add(cal = new JRadioButton("CalFuzzer",true));
		anotherPanel.add(rv = new JRadioButton("Rv-Predict"));
		anotherPanel.add(date = new JRadioButton("DATE"));
		anotherPanel.add(other = new JRadioButton("other tool"));
		btCompare.setBounds(20, 90, 100, 40);
		anotherPanel.add(btCompare);
		btExample.setBounds(300, 90, 150, 40);
		btExample.setEnabled(false);
		anotherPanel.add(btExample);
//		chooseResult.setFileSelectionMode(JFileChooser.FILES_ONLY );  
//        chooseResult.showDialog(new JLabel(), "选择结果文件");  
//        chooseResult.setBounds(750, 50, 100, 50);
		btChooseResult.setEnabled(false);
		btChooseResult.setBounds(150, 90, 100, 40);
        anotherPanel.add(btChooseResult);
        anotherPanel.setVisible(false);
		cal.setBounds(20, 20, 100, 20);
		rv.setBounds(140, 20, 100, 20);
		date.setBounds(260, 20, 100, 20);
		other.setBounds(380, 20, 100, 20);
		ButtonGroup group = new ButtonGroup();
		group.add(cal);
		group.add(rv);
		group.add(date);
		group.add(other);		
		panel.add(anotherPanel);
		this.add(panel);
		if(jText.getText().equals("")||jText==null){
			runScript.setEnabled(false);
		}
		cal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "CalFuzzer";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}
			
		});
		rv.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ComparisonResult.tool = "Rv-Predict";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}
		});
		date.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "DATE";
				btCompare.setEnabled(true);
				btExample.setEnabled(false);
			}
			
		});
		other.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ComparisonResult.tool = "other";
				btCompare.setEnabled(false);
				btChooseResult.setEnabled(true);
				btExample.setEnabled(true);
			}
			
		});
		btExample.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ExampleFrame ex = new ExampleFrame();
				System.out.println("开始查看样例");
				ex.setSize(500, 500);
				ex.setLocationRelativeTo(null);
				ex.setVisible(true);
				ex.setTitle("样例");
			}
		});
		chooseFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc= new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );  
		        jfc.showDialog(new JLabel(), "选择脚本");  
		        jfc.setBounds(750, 50, 100, 50);
		        File file=jfc.getSelectedFile();
		        if(file!=null){
		        	fileAbsolutePath = file.getAbsolutePath();
		        	System.out.println("脚本路径："+file.getAbsolutePath());
		        	String commands = readFileByLines(fileAbsolutePath);
		        	jText.setText(commands);
		        	if(!(jText.getText().equals("")||jText==null)){
		    			runScript.setEnabled(true);
		    		}
		        }
			}			
		});
		runScript.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(System.getProperty("user.dir"));
				String writePosition = System.getProperty("user.dir").replace('\\', '/');
				int position = writePosition.lastIndexOf('/'); 
				writePosition = writePosition.substring(0, position);
				System.out.println("writePosition:"+writePosition);
				String command = "";
//				runCommands(fileAbsolutePath,writePosition);
//				runCommands("E:\\courseResource\\programResearch\\tool\\commands.bat",writePosition);
				for(int i=0;i<Script.scripts.size();i++){
					String temp = Script.scripts.get(i);
					try {
						String tempFile = writePosition + "/tempFile.bat";
						FileWriter writer = new FileWriter(tempFile, false);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//						writer.write("\necho \"-->start " + "on " + df.format(new Date()) + "<--\"\n");
						writer.write(deleteName(temp));
//						writer.write("\n echo \"-->end<--\"\n");
						writer.close();
						runCommands(tempFile,writePosition,temp,getName(temp));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				anotherPanel.setVisible(true);
			}
		});
	}
	
	public String getName(String command){
		String name = command;
		int index = command.indexOf(']');
		name = command.substring(1, index);
		return name;
	}
	
	public String deleteName(String command){
		int index = command.indexOf(']');
		command = command.substring(index + 1);
		return command;
	}
	
	public void runCommands(String scriptFile,String filePath,String command,String programName){
		try {
//			String command = "java -cp E:\\courseResource\\programResearch\\benchmark相关\\benchmarks汇总\\calfuzzer_calfuzzer\\CalFuzzer_TestRace1\\bin benchmarks.testcases.TestRace1";
//			command = "cmd /c E:\\tsmart\\source-of-key\\apache-ant-1.9.6\\bin\\ant -f E:\\courseResource\\programResearch\\benchmark\\calfuzzer\\calfuzzer2\\calfuzzer\\run.xml test_race1";
//			command = "E:\\courseResource\\programResearch\\tool\\test.bat";
//			command = "E:\\tsmart\\source-of-key\\apache-ant-1.9.6\\bin\\ant -f E:\\courseResource\\programResearch\\benchmark\\calfuzzer\\calfuzzer2\\calfuzzer\\run.xml test_race1";
			Process proc = Runtime.getRuntime().exec(scriptFile);
			BufferedInputStream in = new BufferedInputStream(proc.getInputStream());  
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
            String lineStr;
            FileWriter writer = new FileWriter(filePath + "/result.txt", true);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            writer.write("\n>>>>>start command " + command + " about " + programName + " on " + df.format(new Date()) + " <<<<<\n");
            while ((lineStr = inBr.readLine()) != null){  
                //获得命令执行后在控制台的输出信息  
                System.out.println(lineStr);// 打印输出信息  
            	writer.write(lineStr + "\n");
            }
            //检查命令是否执行失败。  
            if (proc.waitFor() != 0) {  
                if (proc.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
                    System.err.println("命令执行失败!");  
                writer.write("命令执行失败");
            }  
            writer.write("\n>>>>>end<<<<<\n");
            inBr.close();  
            in.close();  
            writer.close();
		}catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        String commands = "";
        try{  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while((tempString = reader.readLine())!= null){  
                // 显示行号  
                System.out.println("line " + line + ": " + tempString);  
                line++;  
                commands += tempString + "\n";
                Script.scripts.add(tempString);
            }  
            reader.close();
        }catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }
		return commands;  
    }  
}

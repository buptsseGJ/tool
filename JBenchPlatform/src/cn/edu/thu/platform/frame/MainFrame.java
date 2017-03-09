package cn.edu.thu.platform.frame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;

import cn.edu.thu.platform.parser.DomToEntity;
import cn.edu.thu.platform.parser.ParseXml;

/**
 * 主界面 主要功能： 1）读取benchamrk readSuites 2）管理benchmark manageSuites 3）运行程序
 * runProgram 4）程序脚本 scriptFile
 * 
 */
public class MainFrame extends JFrame {

	private JButton readSuites = new JButton("读benchmark");
	private JButton manageSuites = new JButton("管理benchmark");
	private JButton runProgram = new JButton("运行程序 ");
	private JButton scriptFile = new JButton("程序脚本");

	public MainFrame() {
		JPanel p1 = new ButtonPanel();
		p1.add(readSuites);
		p1.add(manageSuites);
		p1.add(runProgram);
		p1.add(scriptFile);
		add(p1, BorderLayout.CENTER);

		// 读benchmark 监听函数
		readSuites.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("开始文件选择");
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null) {
					String fileAbsolutePath = file.getAbsolutePath();
					ParseXml parser = new ParseXml();
					Document validationResult = parser
							.validateXml(fileAbsolutePath);
					if (validationResult != null) {
						System.out.println("正确");
						DomToEntity convert = new DomToEntity();
						convert.startDom(validationResult);
					} else {
						System.out.println("错误");
					}
				}
			}
		});
		// 管理benchmark 监听函数
		manageSuites.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageSuitesFrame msf = new ManageSuitesFrame();
				System.out.println("开始管理benchmark");
				msf.setSize(1000, 1000);
				msf.setLocationRelativeTo(null);
				msf.setVisible(true);
				msf.setTitle("管理");
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
		// 程序脚本 监听函数
		scriptFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 弹出 实现 程序脚本 的新窗口
				SelectScriptFrame ssf = new SelectScriptFrame();
				System.out.println("开始脚本操作");
				ssf.setSize(1160, 1000);
				ssf.setLocationRelativeTo(null);
				ssf.setVisible(true);
				ssf.setTitle("脚本");
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame mainFrame = new MainFrame();
		mainFrame.setSize(1200, 800);
		mainFrame.setTitle("测试平台");
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		// frame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	static class ButtonPanel extends JPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}
}



import java.awt.*; 
import javax.swing.*;
public class JF extends JFrame {
	JPanel jp=(JPanel)this.getContentPane();
	public JF()
	{
		jp.setLayout(null);//不设置的话是无法设置jpanel的大小。等同于窗口的大小。
		JPanel jpanel=new JPanel();
		jpanel.setSize(300,200);
		jpanel.setBounds(new Rectangle(50,50,300,200));//设置jpanel的左边距、上边距、长度、高度，在jp没设置setLayout(null);是无效的
		jpanel.setBackground(Color.GRAY);
		jpanel.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE)); 
		jp.add(jpanel);
	}
	public static void main(String[] args)
	{
		JF frame=new JF();
		frame.setSize(500,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}


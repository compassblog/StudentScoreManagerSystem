package StudentManageSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



@SuppressWarnings("serial")
public class StudentManageSystem1 extends JFrame implements ActionListener{
	Container contentPane;
	JMenuBar myMenuBar = new JMenuBar();
	JMenu aboutSystem;
	JMenu searchModel;
	
	SearchPane1 searchPane1;
	
	public StudentManageSystem1(JFrame owner)
	{
		super("学生成绩查询");
		contentPane	= getContentPane();
		contentPane.setLayout(new BorderLayout());
		//setTitle("学生成绩管理系统");

		/*添加菜单栏和菜单项*/
		
		setJMenuBar(myMenuBar);
		searchModel  = new JMenu("点击查询");
		JRadioButtonMenuItem local  = new JRadioButtonMenuItem("按学号姓名查询");
		searchModel.add(local);
		
		local.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int i;
				for(i = 0;i<searchModel.getItemCount();i++)
					searchModel.getItem(i).setSelected(false);
				((JRadioButtonMenuItem)e.getSource()).setSelected(true);
				CardLayout cl = (CardLayout)(searchPane1.getLayout());
				cl.show(searchPane1, e.getActionCommand());
			}
		});
		
		aboutSystem = new JMenu("关于系统");
		myMenuBar.add(searchModel);
		//myMenuBar.add(aboutSystem);

		/*添加背景面板*/
		searchPane1 = new SearchPane1(this);
		contentPane.add(searchPane1,BorderLayout.SOUTH);
		
		setSize(550,300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width-getWidth())/2;
		int y = (height-getHeight())/2;
		setLocation(x,y);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//若这句话不关闭，则关闭其中一个主窗体时，另一个主窗体也会随之关闭
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
	}

}
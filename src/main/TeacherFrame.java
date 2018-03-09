package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import DataBase.DBcon;
import Dialog.AddStudentDialog;
import Dialog.QueryStudentScore;
import Dialog.QueryStudentDialog;
import Dialog.TeacherDialog;
import Dialog.UpdatePassDilog;
import Dialog.UpdateStudentDialog;
import StudentManageSystem.TeacherManageSystem;

@SuppressWarnings("serial")
public class TeacherFrame extends JFrame implements ActionListener{
	JMenuBar  menubar = new JMenuBar();
	
	JMenu   userMenu = new JMenu("其他");
	JMenuItem  passMenuItem = new JMenuItem("修改密码");
	JMenuItem  exitMenuItem = new JMenuItem("退出系统");
	JMenu   gradeMenu = new JMenu("成绩管理");
	JMenuItem inputMenuItem = new JMenuItem("点击管理");
	JMenu   aboutSystemMenu = new JMenu("关于系统");
	JMenuItem aboutMenuItem = new JMenuItem("关于");
	JMenuItem helpMenuItem = new JMenuItem("帮助");
	JLabel  welcomeLabel = new JLabel("学生成绩管理系统");
	
	TeacherDialog teacherDialog;
	QueryStudentScore course;
	QueryStudentDialog queryStudentDialog;
	UpdateStudentDialog updateStudentDialog;
	UpdatePassDilog updatePassword;
	AddStudentDialog addStudentDialog;
	TeacherManageSystem teacherManager;
	
	JPanel panel =new JPanel();
	JLabel useridT,usernameT,sexT,GradeT;
	JLabel useridY,usernameY,sexY,GradeY;
	Container c;
	
	String a;
	public TeacherFrame(String s){
		try{
		a=s;
		this.setTitle("学生成绩管理系统---教师界面");
		//构造主菜单
		this.setJMenuBar(menubar);
		c=getContentPane();
		c.setLayout(new BorderLayout());
	
		menubar.add(gradeMenu);
		menubar.add(userMenu);
		menubar.add(aboutSystemMenu);
		//构造用户管理菜单
		userMenu.add(passMenuItem);
		userMenu.add(exitMenuItem);
		//构造成绩管理菜单
		gradeMenu.add(inputMenuItem);
		aboutSystemMenu.add(aboutMenuItem);
		aboutSystemMenu.add(helpMenuItem);
		
		panel.setLayout(new GridLayout(2,2,0,0));
		useridT=new JLabel("职工号:");
		usernameT=new JLabel("姓名:");
		sexT=new JLabel("性别:");
		GradeT=new JLabel("年级:");
		
		DBcon db = new DBcon();
		ResultSet rs;
		rs = db.query("select * from users where userid='"+s+"'");
		rs.next();
		String username = rs.getString("username");
		String sex = rs.getString("sexy");
		String grade = rs.getString("classgrade");
		
		useridY=new JLabel(s);
		usernameY=new JLabel(username);
		sexY=new JLabel(sex);
		GradeY=new JLabel(grade);
		
		useridT.setFont(new java.awt.Font("DialogInput", 1, 20));
		usernameT.setFont(new java.awt.Font("DialogInput", 1, 20));
		sexT.setFont(new java.awt.Font("DialogInput", 1, 20));
		GradeT.setFont(new java.awt.Font("DialogInput", 1, 20));
		useridY.setFont(new java.awt.Font("DialogInput", 1, 20));
		usernameY.setFont(new java.awt.Font("DialogInput", 1, 20));
		sexY.setFont(new java.awt.Font("DialogInput", 1, 20));
		GradeY.setFont(new java.awt.Font("DialogInput", 1, 20));
		panel.add(useridT);
		panel.add(useridY);
		panel.add(usernameT);
		panel.add(usernameY);
		panel.add(sexT);
		panel.add(sexY);
		panel.add(GradeT);
		panel.add(GradeY);
		this.getContentPane().add(panel);
	
		//构造欢迎页面
		welcomeLabel.setFont(new java.awt.Font("Dialog", 0, 48));
		//welcomeLabel.setHorizontalAlignment(SwingConstants.SOUTH);
		welcomeLabel.setForeground(Color.blue);
		//this.getContentPane().add(welcomeLabel);
		JPanel panel1=new JPanel();
		panel1.add(welcomeLabel);
		c.add(panel1,BorderLayout.SOUTH);
		c.add(panel,BorderLayout.CENTER);
		
		//为主菜单注册监听器
		passMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		inputMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
			
		//显示系统主界面
		this.setSize(400, 230);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void actionPerformed(ActionEvent e) {
		//主菜单控制
		if(e.getSource()==passMenuItem){//修改密码
			//创建密码管理界面
			updatePassword =new UpdatePassDilog(this);
			}	
		else if(e.getSource()==exitMenuItem){//退出系统
			if(JOptionPane.showConfirmDialog(this, "确认要退出系统？","退出",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
				System.exit(0);
			}			
		}else if(e.getSource()==inputMenuItem){//录入成绩
			//创建成绩录入面板
			teacherManager = new TeacherManageSystem(this,a);
		}else if(e.getSource()==aboutMenuItem){//关于
			//创建修改密码界面
			JOptionPane.showMessageDialog(null,"关于：学生成绩管理系统 Version 1.0\n系统开发参与者：\n系统数据库操作与业务层&组长：孔潭活\n系统整体框架分析与界面设计层：何德新\n数据库的设计分析与调试分析层：冯丽茂");
		}else if(e.getSource()==helpMenuItem){//帮助
			//创建修改密码界面
			JOptionPane.showMessageDialog(null,
					"本系统教师端的核心功能：\n" +
					"教师可以根据全局查询管理学生的成绩，其中包括：\n" +
					"录入学生各科的成绩，对学生成绩排序等，当教师录入\n学生成绩后仍然可以修改学生成绩，一旦提交则不可以在修改。\n" + 
					"                         感谢使用本系统！"
					);
		}
	}
}

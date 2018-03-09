package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

import DataBase.DBcon;
import Dialog.AddStudentDialog;
import Dialog.QueryStudentScore;
import Dialog.Query1StudentScore;
import Dialog.Query2StudentScore;
import Dialog.QueryStudentDialog;
import Dialog.TeacherDialog;
import Dialog.Update1PassDilog;
import Dialog.UpdateStudentDialog;
import StudentManageSystem.StudentManageSystem1;

@SuppressWarnings("serial")
public class StudentFrame extends JFrame implements ActionListener{
	JMenuBar  menubar = new JMenuBar();

	JMenu   gradeMenu = new JMenu("成绩查询");
	JMenuItem query1MenuItem = new JMenuItem("根据学号或者姓名查询");
	JMenuItem query3MenuItem = new JMenuItem("根据科目查询");
	JMenuItem query2MenuItem = new JMenuItem("根据学期查询");
	
	JMenu   studentMenu = new JMenu("信息管理");
	JMenuItem queryStudentMenuItem = new JMenuItem("信息修改");
	
	JMenu   otherMenu = new JMenu("其他");
	JMenuItem otherFirstMenuItem = new JMenuItem("修改密码");
	JMenuItem otherSecondMenuItem = new JMenuItem("退出系统");
	
	JMenu   aboutSystemMenu = new JMenu("关于系统");
	JMenuItem aboutMenuItem = new JMenuItem("关于");
	JMenuItem helpMenuItem = new JMenuItem("帮助");
	
	JLabel  welcomeLabel = new JLabel("学生成绩管理系统");
	
	TeacherDialog teacherDialog;
	QueryStudentScore course;
	Query1StudentScore course1;
	Query2StudentScore course2;
	QueryStudentDialog queryStudentDialog;
	UpdateStudentDialog updateStudentDialog;
	Update1PassDilog updatePassword;
	AddStudentDialog addStudentDialog;
	StudentManageSystem1 localManage;
	
	JPanel panel =new JPanel();
	JLabel useridT,usernameT,sexT,GradeT;
	JLabel useridY,usernameY,sexY,GradeY;
	Container c;
	
	public StudentFrame(String s) throws Exception{
		this.setTitle("学生成绩管理系统---学生界面");
		c=getContentPane();
		c.setLayout(new BorderLayout());
		
		panel.setLayout(new GridLayout(2,2,0,0));
		useridT=new JLabel("学号:");
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
		welcomeLabel.setFont(new java.awt.Font("DialogInput", 1, 48));
		welcomeLabel.setForeground(Color.blue);
		JPanel panel1=new JPanel();
		panel1.add(welcomeLabel);
		c.add(panel1,BorderLayout.SOUTH);
		c.add(panel,BorderLayout.CENTER);
		
		//构造主菜单
		this.setJMenuBar(menubar);
		menubar.add(gradeMenu);
		menubar.add(studentMenu);
		menubar.add(otherMenu);
		menubar.add(aboutSystemMenu);

		//构造成绩查询菜单
		gradeMenu.add(query1MenuItem);
		gradeMenu.add(query3MenuItem);
		gradeMenu.add(query2MenuItem);
		//构造学生管理菜单
		studentMenu.add(queryStudentMenuItem);
		//构建其他菜单
		otherMenu.add(otherFirstMenuItem);
		otherMenu.add(otherSecondMenuItem);
		
		aboutSystemMenu.add(aboutMenuItem);
		aboutSystemMenu.add(helpMenuItem);

		//为主菜单注册监听器
		query1MenuItem.addActionListener(this);
		query2MenuItem.addActionListener(this);
		query3MenuItem.addActionListener(this);
		queryStudentMenuItem.addActionListener(this);
		
		otherFirstMenuItem.addActionListener(this);
		otherSecondMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
			
		//显示系统主界面
		this.setSize(400, 240);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==query1MenuItem){
			//按姓名学号查询
			localManage = new StudentManageSystem1(this);
			//
		}else if(e.getSource()==query3MenuItem){
			//按科目查询
			course1 = new Query1StudentScore(this); 
		}else if(e.getSource()==query2MenuItem){
			//按学期查询
			course2 = new Query2StudentScore(this);
		}else if(e.getSource()==queryStudentMenuItem){//学生查询
			//创建学生查询界面
			queryStudentDialog = new QueryStudentDialog(this);
		}else if(e.getSource()==otherFirstMenuItem){//学生查询
			
			updatePassword =new Update1PassDilog(this);
		}else if(e.getSource()==otherSecondMenuItem){
			
			if(JOptionPane.showConfirmDialog(this, "确认要退出系统？","退出",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
				System.exit(0);
			}
		}else if(e.getSource()==aboutMenuItem){//关于
			//创建修改密码界面
			JOptionPane.showMessageDialog(null,"关于：学生成绩管理系统 Version 1.0\n系统开发参与者：\n系统数据库操作与业务层&组长：孔潭活\n系统整体框架分析与界面设计层：何德新\n数据库的设计分析与调试分析层：冯丽茂");
		}else if(e.getSource()==helpMenuItem){//帮助
			//创建修改密码界面
			JOptionPane.showMessageDialog(null,
					"本系统学生端的核心功能：\n" +
					"根据自己的学号或者姓名查询自己的成绩，其中包括：\n" +
					"各科的成绩、平均分、班级排名等；\n" + 
					"                   感谢使用本系统！"
					);
		}
		
	}
}

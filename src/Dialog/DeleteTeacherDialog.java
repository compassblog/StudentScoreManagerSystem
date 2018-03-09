package Dialog;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DataBase.DBcon;
/*---------这个类里写的是学生管理中的学生信息查询模块-----------------咩*/
@SuppressWarnings("serial")
public class DeleteTeacherDialog extends JDialog implements ActionListener{
	private JTable jTable1;
	public  JButton query1,delete;
	TextField t1;
	public JPanel p1,p2; 
	public DeleteTeacherDialog(JFrame owner)
	{
		super(owner,"删除教师信息");
		t1=new TextField(10);
		query1=new JButton("查询");
		delete=new JButton("删除");
		this.setModal(true);
		/*创建查询结果面板*/
		TableModel jTable1Model = new DefaultTableModel(new String[0][0] ,	new String[] { "工号", "姓名","性别","任教班级" });
		jTable1 = new JTable();
		jTable1.setModel(jTable1Model);
		JScrollPane jScrollPane1 = new JScrollPane(jTable1);
		
		/*创建按钮面板*/
		p1=new JPanel();
		p1.add(new JLabel("工号："));
		p2=new JPanel();
		p1.add(t1);
		p1.add(query1);
		p1.add(delete);
		query1.addActionListener(this);
		delete.addActionListener(this);
		add(p1,BorderLayout.NORTH);
		getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		setBounds(500,300,400,300);
		setVisible(true);
	}
 public void actionPerformed(ActionEvent e) {
	DefaultTableModel tm = new DefaultTableModel(new String[0][0] ,	new String[] { "工号", "姓名","性别","任教班级" });
	jTable1.setModel(tm);
		if(e.getSource()==query1)
		{
		    try{
			   Class.forName(DBcon.DBDRIVER);
		    }
		    catch(ClassNotFoundException a){
		   	 System.out.println(""+a.getMessage());
			}
		   try{ 
	   	     Connection con;
	         String id=t1.getText();
	         String no="";
			 String name="";
			 String sexy="";
			 String tclass="";
			 con=DriverManager.getConnection(DBcon.DBURL,DBcon.DBUSER,DBcon.DBPASS);
			 Statement sql=con.createStatement();
			 String s="select * from users where userid='"+id+"' and usertype ='教师'";
			 ResultSet rs=sql.executeQuery(s);
			 rs.next();
			 no=rs.getString("userid").trim();
			 name = rs.getString("username").trim();
			 sexy = rs.getString("sexy").trim();
			 tclass = rs.getString("classgrade").trim();
			 tm.addRow(new String[]{no,name,sexy,tclass});
			 rs.close();
			 con.close(); 
			   }
			catch(SQLException a)
			{
				JOptionPane.showMessageDialog(null, "用户名不存在！");
				System.out.println(a.getMessage());
			}
		}
		else if(e.getSource()==delete){
			try{
				   Class.forName(DBcon.DBDRIVER);
			    }
			    catch(ClassNotFoundException a){
			   	 System.out.println(""+a.getMessage());
				}
			   try{ 
		   	     Connection con;
		         String id=t1.getText();
				 con=DriverManager.getConnection(DBcon.DBURL,DBcon.DBUSER,DBcon.DBPASS);
				 Statement sql=con.createStatement();
				 String s="delete from users where userid='"+id+"'";
				 sql.execute(s);
				 JOptionPane.showMessageDialog(null, "教师信息删除成功！");
				 con.close(); 
				   }
				catch(SQLException a)
				{
					System.out.println(a.getMessage());
				}
			//this.dispose();
		}
		  
}
}
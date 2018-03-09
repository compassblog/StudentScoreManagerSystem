package Dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import Users.User;
import DataBase.DBcon;
/*---------这个类里写的是成绩管理中的成绩查询模块-----------------咩*/

@SuppressWarnings("serial")
public class Query1StudentScore extends JDialog implements ActionListener{
	private JTextField jtfCourseNo;
	private JButton jbtCancel;
	private JButton jbtInput;
	private JTable jTable1;
	String length;
	int i=0;
	public ArrayList<User> list=new ArrayList<User>();
	public Query1StudentScore(JFrame owner){
		super(owner,"成绩查询");
		this.setSize(350, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);//modal - 指定 dialog 是否阻止在显示的时候将内容输入其他窗口
		/*--------------------添加面板开始查询------------------*/
		JPanel jPanel1 = new JPanel();
		jtfCourseNo = new JTextField(10);
		jbtInput = new JButton("开始查询");
		jPanel1.add(new JLabel("科目名称："));
		jPanel1.add(jtfCourseNo);
		jPanel1.add(jbtInput);
		getContentPane().add(jPanel1, BorderLayout.NORTH);
		
		/*--------------------添加按钮面板------------------*/
		JPanel jPanel2 = new JPanel();
		jbtCancel = new JButton("退出");
		jPanel2.add(jbtCancel);		
		getContentPane().add(jPanel2, BorderLayout.SOUTH);
/*--------------------这个是面板中的表格------------------*/
		TableModel jTable1Model = new DefaultTableModel(new String[0][0] ,	new String[] { "学号", "姓名","科目名称","成绩","学期"});
		jTable1 = new JTable();		
		jTable1.setModel(jTable1Model);
		JScrollPane jScrollPane1 = new JScrollPane(jTable1);
		getContentPane().add(jScrollPane1, BorderLayout.CENTER);	
		
		/*--------------------添加监听按钮------------------*/
		jbtInput.addActionListener(this);
		jbtCancel.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbtInput){
			if(jtfCourseNo.getText().length()==0){
				JOptionPane.showMessageDialog(null, "请输入科目名称！");
				return ;
			}
			 try{
				   Class.forName(DBcon.DBDRIVER);
			      }
			    catch(ClassNotFoundException a){
			   	 System.out.println(""+a.getMessage());
					
				  }
			   try{ 
				   /*----------这段代码是从数据库中将保存的学生信息取出来----------*/
			   	     Connection con;
			   	     String no="";
					 String name="";
					 String course="";
					 String score="";
					 String item="";
			         String courseName=jtfCourseNo.getText();
			         con=DriverManager.getConnection(DBcon.DBURL,DBcon.DBUSER,DBcon.DBPASS);
			         Statement sql=con.createStatement();			         
			         
			         String sq="select count(*) from users join course on users.userid=course.id where name='"+courseName+"'";
			         String q="SELECT userid,username,name,score,item from users join course on users.userid=course.id where name='"+courseName+"'";
			         ResultSet rs=sql.executeQuery(sq);
			         while(rs.next()){
			        	  length=rs.getString(1);
			         }
			         rs.close();
			         
			         /*这段代码是将取出来得值依次按照顺序输出*/
			         ResultSet rs1=sql.executeQuery(q);
			         User []user=new User[Integer.parseInt(length)];
			         while(rs1.next()){
			        	no=rs1.getString(1);
			        	name=rs1.getString(2);
			        	course=rs1.getString(3);
			        	score=rs1.getString(4);
			        	item = rs1.getString(5);
			        	user[i]=new User(no, name, null,null,course, score,item);
			        	list.add(user[i]);			        	
			        }			     
			        DefaultTableModel tm = new DefaultTableModel(new String[0][0] ,	new String[] { "学号", "姓名","科目名称","成绩","学期" });
					jTable1.setModel(tm);
			         
			     	for(User  s : list){
			     		tm.addRow(new String[]{s.getUserNo(),s.getName(),s.getCoursename(),s.getScore(),s.getItem()});
			     	}
					sql.close();
				    con.close();
			   }
			    catch(SQLException a)
			    {
			    	System.out.println(a.getMessage());
			    }
		}
		else if(e.getSource()==jbtCancel){
			this.dispose();
		}
	}

}

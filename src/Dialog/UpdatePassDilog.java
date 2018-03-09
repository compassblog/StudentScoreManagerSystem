package Dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DataBase.DBcon;
/*---------这个类里写的是用户管理中的密码修改模块-----------------咩*/
@SuppressWarnings("serial")
public class UpdatePassDilog extends JDialog implements ActionListener{
	private JTextField jtfStudentID;
	private JPasswordField jtfStudentOldpassword;
	private JPasswordField jtfStudentNewpassword;
	private JPasswordField jtfStudentPassword;
	JButton Save;
	JButton Reset;
	JButton Cancle;
	public UpdatePassDilog(JFrame owner){
		super(owner,"密码修改");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);
		//创建对象
		JPanel jPanel1 = new JPanel();
		jtfStudentID=new JTextField(10);
		jtfStudentOldpassword = new JPasswordField(10);
		jtfStudentNewpassword = new JPasswordField(10);
		jtfStudentPassword = new JPasswordField(10);
		jPanel1.add(new JLabel("输入账号："));
		jPanel1.add(jtfStudentID);
		jPanel1.add(new JLabel("旧密码："));
		jPanel1.add(jtfStudentOldpassword);
		jPanel1.add(new JLabel("新密码："));
		jPanel1.add(jtfStudentNewpassword);
		jPanel1.add(new JLabel("再次确认："));
		jPanel1.add(jtfStudentPassword);
		jPanel1.setLayout(new GridLayout(5, 2));
		getContentPane().add(jPanel1);
		//创建按钮面板
		JPanel jPanel2 = new JPanel();
		Save = new JButton("保存");
		Reset = new JButton("重置");
		Cancle = new JButton("退出");
		jPanel2.add(Save);
		jPanel2.add(Reset);		
		jPanel2.add(Cancle);
		getContentPane().add(jPanel1);
		getContentPane().add(jPanel2, BorderLayout.SOUTH);
		
		//添加监听按钮
		Save.addActionListener(this);
		Reset.addActionListener(this);
		Cancle.addActionListener(this);
		this.setVisible(true);
}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(jtfStudentID.getText()==null&&jtfStudentOldpassword.getText()==null&&jtfStudentNewpassword.getText()==null&&jtfStudentPassword.getText()==null)
		{
			JOptionPane.showMessageDialog(null, "对不起内容不能为空", "Welcome", JOptionPane.YES_NO_OPTION);
		}
		else{
			if(e.getSource()==Save)
		{
				//String newpwd=jtfStudentNewpassword.getText();
				String newpwd = new String(jtfStudentNewpassword.getPassword());
				String pwd=new String(jtfStudentNewpassword.getPassword());
				if(newpwd.equals(pwd)){
					try{
						 Class.forName(DBcon.DBDRIVER);
				          }
				       catch(ClassNotFoundException a){
				           System.out.println(""+a.getMessage());
				          }
				       try{ 
				           Connection con;
				           String id=jtfStudentID.getText();
				          // String Oldpassword=new String(jtfStudentOldpassword.getPassword()).trim();
				           String Newpassword=new String(jtfStudentNewpassword.getPassword()).trim();
				           //String StudentPassword=new String(jtfStudentPassword.getPassword()).trim();
	
				           con=DriverManager.getConnection(DBcon.DBURL,DBcon.DBUSER,DBcon.DBPASS);
				           Statement sql=con.createStatement();
				           String s="UPDATE loginusers SET password='"+Newpassword+"' where adminid='"+id+"'";
				           sql.executeUpdate(s);
				           sql.close();
				           con.close();
				           JOptionPane.showMessageDialog(null, "密码修改成功", "Welcome", JOptionPane.YES_NO_OPTION);
				           }
				          catch(SQLException a)
				         {
				        	  System.out.println(a.getMessage());
				         }
				}else{
					JOptionPane.showMessageDialog(null, "你所输入的新密码不一致，请重新输入！");
					jtfStudentID.setText("");
					jtfStudentOldpassword.setText("");
					jtfStudentNewpassword.setText("");
					jtfStudentPassword.setText("");
					 }
		}
			else if(e.getSource()==Reset){
				jtfStudentID.setText("");
				jtfStudentOldpassword.setText("");
				jtfStudentNewpassword.setText("");
				jtfStudentPassword.setText("");
			}else if(e.getSource()==Cancle){
				this.dispose();
			}
		}
	}
}

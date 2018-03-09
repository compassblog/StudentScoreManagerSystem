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
public class UpdateInputPower extends JDialog implements ActionListener{
	private JTextField jtfStudentID;
	private JPasswordField jtfStudentOldpassword;
	private JTextField jtfType;
	JButton Save;
	JButton Reset;
	JButton Cancle;
	public UpdateInputPower(JFrame owner){
		super(owner,"权限修改");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);
		//创建对象
		JPanel jPanel1 = new JPanel();
		jtfStudentID=new JTextField(10);
		jtfStudentOldpassword = new JPasswordField(10);
		jtfType = new JTextField(10);
		jPanel1.add(new JLabel("教师账号："));
		jPanel1.add(jtfStudentID);
		jPanel1.add(new JLabel("密码："));
		jPanel1.add(jtfStudentOldpassword);
		jPanel1.add(new JLabel("权限修改为："));
		jPanel1.add(jtfType);
		jPanel1.setLayout(new GridLayout(4, 2));
		getContentPane().add(jPanel1);
		//创建按钮面板
		JPanel jPanel2 = new JPanel();
		Save = new JButton("确定");
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
		if(jtfStudentID.getText()==null&&jtfStudentOldpassword.getText()==null&&jtfType.getText()==null)
		{
			JOptionPane.showMessageDialog(null, "对不起内容不能为空", "Welcome", JOptionPane.YES_NO_OPTION);
		}
		else{
			if(e.getSource()==Save)
		{
					try{
						 Class.forName(DBcon.DBDRIVER);
				          }
				       catch(ClassNotFoundException a){
				           System.out.println(""+a.getMessage());
				          }
				       try{ 
				           Connection con;
				           String id=jtfStudentID.getText();
				           String pass = jtfStudentOldpassword.getText();
				           String oldtype = jtfType.getText().trim();
				           @SuppressWarnings("unused")
						int newtype = Integer.parseInt(oldtype);
				           con=DriverManager.getConnection(DBcon.DBURL,DBcon.DBUSER,DBcon.DBPASS);
				           Statement sql=con.createStatement();
				           String s="UPDATE users SET type='"+oldtype+"' where userid='"+id+"' and userpwd='"+pass+"'";
				           sql.executeUpdate(s);
				           sql.close();
				           con.close();
				           JOptionPane.showMessageDialog(null, "权限修改成功", "Welcome", JOptionPane.YES_NO_OPTION);
				           }
				          catch(SQLException a)
				         {
				        	  System.out.println(a.getMessage());
				         }
		}
			else if(e.getSource()==Reset){
				jtfStudentID.setText("");
				jtfStudentOldpassword.setText("");
				jtfType.setText("");
			}else if(e.getSource()==Cancle){
				this.dispose();
			}
		}
	}
}

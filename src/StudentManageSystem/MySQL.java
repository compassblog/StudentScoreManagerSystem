package StudentManageSystem;

import java.sql.*;
import javax.swing.*;

class MySQL
{
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	public MySQL()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?useUnicode=true&characterEncoding=UTF-8","root","root");
			//Connection con = DriverManager.getConnection("jdbc:odbc:zz", "", "");
			stmt = con.createStatement();
		}catch(ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"未加载jdbc驱动\n"+e.toString());
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"数据库语法错误\n"+e.toString());
		}
	}
	
	public void search(String s)
	{
		try
		{
			rs = stmt.executeQuery(s);
		}catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"执行查询语句出错\n"+e.toString());
		}
	}
	public int insert(String s)
	{
		try
		{
			return stmt.executeUpdate(s);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"执行插入语句错误\n"+e.toString());
		}
		return -1;
	}
	
	public int delete(String s)
	{
		try
		{
			return stmt.executeUpdate(s);
		}catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"执行删除语句出错\n"+e.toString());
		}
		return -1;
	}
	
	public int update(String s)
	{
		try
		{
			int temp=stmt.executeUpdate(s);
			return temp;
		}catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"执行修改语句出错\n"+e.toString());
		}
		return -1;
	}
	
	public ResultSet getResultSet()
	{
		return rs;
	}
	
	public void closeConnection()
	{
		try
		{
			stmt.close();
			con.close();
		}catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"关闭数据库连接时错误\n"+e.toString());
		}/*catch(NullPointerException e)
		{
			System.out.println(e.toString());
		}*/

	}
}

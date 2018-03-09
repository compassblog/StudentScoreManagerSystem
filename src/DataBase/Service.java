package DataBase;

import java.sql.*;

public class Service{
	public static int getAdminId(String user,String pass){
		try{
			DBcon db = new DBcon();
			ResultSet rs = db.query("SELECT adminid from loginusers WHERE username = '"+user+"' AND password = '"+pass+"'");
			if (rs.next()) 
				return rs.getInt(1);
			return 0;
		}catch(Exception e){
			System.err.println("查询用户表出错！");
			System.err.println(e);
			return -1;
		}
	}
	public static int getUserType(String user,String pass){
		try{
			DBcon db = new DBcon();
			ResultSet rs = db.query("SELECT userid from users WHERE userid = '"+user+"' AND userpwd = '"+pass+"'");
			if (rs.next()) 
				return Integer.parseInt(rs.getString(1));
			return 0;
		}catch(Exception e){
			System.err.println("查询用户表出错！");
			System.err.println(e);
			return 0;
		}
	}
}

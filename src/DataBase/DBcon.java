package DataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class DBcon {
	public static final String DBDRIVER = "com.mysql.jdbc.Driver" ;
	// 定义MySQL数据库的连接地址
	public static final String DBURL = "jdbc:mysql://localhost:3306/studentdb?useUnocode=true&characterEncodeing=UTF-8" ;
	// MySQL数据库的连接用户名
	public static final String DBUSER = "root" ;
	// MySQL数据库的连接密码
	public static final String DBPASS = "root" ;
	java.sql.Connection con=null;
	Statement stmt;
	public DBcon(){
		try{
			jbInit();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		con =DriverManager.getConnection(DBURL,DBUSER,DBPASS);//连接数据库
		stmt= con.createStatement();//创建语句对象
	}
	public Statement getStatement(){
		return stmt;
	}
	//用以执行查询的SQL语句
	public ResultSet query(String SELECT_USER_SQL)throws Exception{
		return stmt.executeQuery(SELECT_USER_SQL);
	}
	//用以执行更新的SQL语句
	public void update(String UPDATE_SQL)throws Exception{
		 stmt.executeUpdate(UPDATE_SQL);
	}
	//用以执行增加的SQL语句
	public void add(String ADD_SQL)throws Exception{
		 stmt.executeUpdate(ADD_SQL);
	}
	//用以执行删除的SQL语句
	public void delete(String DELETE_SQL)throws Exception{
		 stmt.executeUpdate(DELETE_SQL);
	}
}

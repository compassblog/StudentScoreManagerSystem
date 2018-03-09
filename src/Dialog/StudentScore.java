package Dialog;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.sql.*;

import javax.swing.table.*;

@SuppressWarnings("serial")
public class StudentScore extends JPanel implements ActionListener{
	@SuppressWarnings("unused")
	private String XYID;
	private String ID;
	private Connection con;
	private Statement sql;
	private ResultSet rs;
	private Statement sql2;
	private ResultSet rs2;
	//存放当前教师所在的学院号
	@SuppressWarnings("unused")
	private String coll_id;
	@SuppressWarnings("rawtypes")
	private Map map_dept=new HashMap();
	@SuppressWarnings("rawtypes")
	private Vector v_dept=new Vector();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox jcb=new JComboBox(v_dept);
	//创建提示信息标签
	private JLabel jl=new JLabel("请选择您要操作的课程");
	//创建存放表头及表格数据的Vector对象
	@SuppressWarnings("rawtypes")
	private Vector v_head=new Vector();
	@SuppressWarnings("rawtypes")
	private Vector v_data=new Vector();
	private JTable jt;//声名表格引用
	private JScrollPane jsp;
	//创建动作按钮，只有公布后的成绩学生才可以看到9
	private JButton jb=new JButton("提交");
	private JButton jb2=new JButton("保存");
	public StudentScore(String coll_id,String ID)
	{
		this.coll_id=coll_id;
		this.ID=ID;
		this.initialData();//初始化数据
		this.initialFrame();//初始化窗体
		this.initialListener();//注册监听器
	}
	public void initialFrame()//初始化窗体
	{   //将控件添加到容器中
		this.setLayout(null);
		jl.setBounds(30,20,150,30);this.add(jl);
		jcb.setBounds(180,20,100,30);this.add(jcb);
		jb.setBounds(325,20,70,30);this.add(jb);
		jb2.setBounds(400,20,70,30);this.add(jb2);
		jt=new JTable(new DefaultTableModel(v_data,v_head));
		jsp=new JScrollPane(jt);
		jsp.setBounds(30,70,500,500);this.add(jsp);
	}
	public void initialListener()//注册监听器
	{
		jcb.addActionListener(this);jb.addActionListener(this);
		jb2.addActionListener(this);
		TableChangeListener tl=new TableChangeListener(sql);
		jt.getSelectionModel().addListSelectionListener(tl);
		jt.getColumnModel().addColumnModelListener(tl);
		jt.getModel().addTableModelListener(tl);
	}
	@SuppressWarnings("unchecked")
	public void initialData()//初始化数据的方法
	{//初始化表头
		v_head.add("课程号");v_head.add("学号");
		v_head.add("姓名");v_head.add("成绩(分)");
		String s="select distinct 课程ID from 成绩 where"+
				" 教师ID='"+ID+"'and (公布=0 or 公布=2)";
		try
		{//查询数据库，将课程名及课程号存入map_dept与v_dept中
			this.initialConnection();
			rs=sql.executeQuery(s);
			while(rs.next()){			
				String cou_id=rs.getString(1);
				String s1="select  课程Name from 课程 where 课程ID='"+cou_id+"'";
				rs2=sql2.executeQuery(s1);
				while(rs2.next()){
					String cou_name=rs2.getString(1);
					map_dept.put(cou_name,cou_id);
					v_dept.add(cou_name);
				}
			}
			rs.close();//关闭结果集
		}
		catch(SQLException e){e.printStackTrace();}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actionPerformed(ActionEvent e)
	{//实现ActionListener接口中的方法
		if(e.getSource()==jcb)
		{//当下拉列表框中的选择内容发生变化时的处理代码
			v_data.removeAllElements();//将v_data清空
			//获得下拉列表选中的课程名
			String cur_cou_name=(String)jcb.getSelectedItem();
			//根据课程名获得课程号
			String cur_cou_id=(String)map_dept.get(cur_cou_name);
			String s2="select 成绩.课程ID,成绩.学生ID,学生.学生Name,成绩 "+
			           "from 成绩,学生 where 成绩.学生ID=学生.学生ID and "+
			            "(公布=0 or 公布=2) and 成绩.课程ID='"+cur_cou_id+"'";//创建sql语句
			try{//执行语句
				rs=sql.executeQuery(s2);
			    while(rs.next()){//将与该课程号相关的未处理的信息存入v_data
			    	Vector v=new Vector();
			    	String cou_id=rs.getString(1);
			    	String stu_id=rs.getString(2);
			    	String stu_name=rs.getString(3);
			    	String score=rs.getDouble(4)+"";
			    	v.add(cou_id);v.add(stu_id);v.add(stu_name);v.add(score);
			    	v_data.add(v);
			    }
			    rs.close();//关闭结果集
			    DefaultTableModel temp1=(DefaultTableModel)jt.getModel();//更新表格模型，
			    temp1.setDataVector(v_data,v_head);
			    temp1.fireTableStructureChanged();//更新显示信息
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		else if(e.getSource()==jb){//当按下公布成绩的按钮时
			try{//获得要公布成绩的课程名
				String cur_cou_name=(String)jcb.getSelectedItem();
				if(cur_cou_name!=null){//更改数据库中的标志列
					String cur_cou_id=(String)map_dept.get(cur_cou_name);
					String s3="update 成绩 set 公布=1 where "+
					            "课程ID='"+cur_cou_id+"' and (公布=0 or 公布=2)";
					@SuppressWarnings("unused")
					int i=sql.executeUpdate(s3);
					JOptionPane.showMessageDialog(this,"提交成功！","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else{//没有选择课程名的错误提示信心
					JOptionPane.showMessageDialog(this,"请先选择课程名称","错误",
					                                JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception ea){ea.printStackTrace();}	
		}
		else if(e.getSource()==jb2){//当按下保存成绩的按钮时
			try{
				String cur_cou_name=(String)jcb.getSelectedItem();
				if(cur_cou_name!=null){//更改数据库中的标志列
					String cur_cou_id=(String)map_dept.get(cur_cou_name);
					String sss="update 成绩 set 公布=2 where 课程ID='"+cur_cou_id+"'and (公布=0 or 公布=2)";
					@SuppressWarnings("unused")
					int i=sql.executeUpdate(sss);
					JOptionPane.showMessageDialog(this,"保存成功！","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else{//没有选择课程名的错误提示信心
					JOptionPane.showMessageDialog(this,"请先选择课程名称","错误",
					                                JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception ea){ea.printStackTrace();}	
		}
	}
	//自定义的初始化数据库连接的方法
	public void  initialConnection()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/成绩管理系统?useSSL=true","root","123456");
			sql=con.createStatement();
			sql2=con.createStatement();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	//关闭连接的方法
	public void closeConn()
	{
		try
		{
			if(rs!=null)
			{rs.close();}
			if(sql!=null)
			{sql.close();}
			if(con!=null)
			{con.close();}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	class TableChangeListener implements ListSelectionListener,
	                          TableModelListener,TableColumnModelListener
	{
		int rowNum,colNum;
		Statement statement;
		public TableChangeListener(Statement statement)
		{this.statement=statement;}
		public void valueChanged(ListSelectionEvent e){//更新行的值
			rowNum=jt.getSelectedRow();
		}
		public void columnSelectionChanged(ListSelectionEvent e){//更新列的值
			colNum=jt.getSelectedColumn();
		}
		public void tableChanged(TableModelEvent e)
		{//当更改的是第三列(分数列)时的处理代码
			if(colNum==3)
			{   //获得输入的数据
				String str=(String)jt.getValueAt(rowNum,colNum);
				//获得该数据所对应的课程号
				String cou_id=(String)jt.getValueAt(rowNum,0);
				//获得该数据所对应学生的学号
				String stu_id=(String)jt.getValueAt(rowNum,1);
				try{//将str转化为Double
					Double d=Double.parseDouble(str);
					if(d<0||d>100)//分数不能小于0且不能大于100
					{//不在范围内，将其设为0
						jt.setValueAt("0",rowNum,colNum);
					}
				}
				catch(Exception ea){//不是数字，将其设为0
					jt.setValueAt("0",rowNum,colNum);
				}
				//将更改的数据同步到数据库
				String s4="update 成绩 set 成绩="+str+" where "+
				           "课程ID='"+cou_id+"' and 学生ID='"+stu_id+"'";
				try{//执行sql语句
					@SuppressWarnings("unused")
					int i=statement.executeUpdate(s4);
				}
				catch(Exception ea){ea.printStackTrace();}
			}
		}
		//实现接口中的其他方法
		public void columnMoved(TableColumnModelEvent e){}
		public void columnRemoved(TableColumnModelEvent e){}
		public void columnMarginChanged(ChangeEvent e){}
		public void columnAdded(TableColumnModelEvent e){}
	}
}


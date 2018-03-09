package StudentManageSystem;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
public class LocalSearchDialog extends JDialog{
	JScrollPane scrollPane;
	DefaultTableModel tableModel;
	JButton yes = new JButton("确定");
	JButton no = new JButton("取消");

	String[] header = {"ID","Name","数学","英语","语文","物理","生物","平均分"};
	public LocalSearchDialog(JFrame f)
	{
		super(f,"",true);
		setBounds(400,300,700,200);
		setLayout(new BorderLayout());
	}
	
	public void showInfo(String message,int mode)
	{
		boolean flag = true;
		try
		{
			flag = search(message,mode);
		}catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this,"出错了！");
		}
		if(flag)
		{
			JPanel pane = new JPanel(new FlowLayout());
			pane.add(yes);
			pane.add(no);
			add(scrollPane,BorderLayout.NORTH);
			add(pane,BorderLayout.SOUTH);
		
			yes.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			no.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			setVisible(true);
		}
	}
	
	public int rank(Vector<Vector<Object>> cellData,Vector<Object> data)
	{
		int length = cellData.size();
		int i,r=1;
		double d = Double.parseDouble(data.get(length-1).toString());
		for(i=1;i<length;i++)
		{
			if(Double.parseDouble(cellData.get(i).get(length-1).toString()) > d)
				r++;
		}
		return r;
	}
	public boolean search(String message,int mode) throws SQLException
	{
		int i,j;
		boolean flag = true;
		String sqlInfo="";
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Vector<Object>> cellData = new Vector<Vector<Object>>();
		
		for(i=0;i<header.length;i++)
			columnNames.addElement(header[i]);
		columnNames.addElement("排名");
		
		if(mode == 1)
			sqlInfo += "select * from studentmanager where ID='"+message+"'";
		else if(mode == 0)
			sqlInfo += "select * from studentmanager where Name='"+message+"'";
		MySQL sql = new MySQL();
		sql.search(sqlInfo);
		ResultSet rs = sql.getResultSet();
		double sum = 0;
		Vector<Object> temp = new Vector<Object>();
		for(i=0;rs.next();i++)
		{
			for(j = 1;j<=header.length-1;j++)
			{
				String s = rs.getString(j);
				temp.addElement(s);
				if(j>2)
					sum += Double.parseDouble(s);
			}
			temp.addElement(sum/5);
			flag = false;
		}
		if(flag)
		{
			JOptionPane.showMessageDialog(this,"学号或姓名不正确！");
			return false;
		}
		sql.search("select * from studentmanager");
		rs = sql.getResultSet();

		int r=1;
		double s=Double.parseDouble(temp.get(header.length-1).toString());
		while(rs.next())
		{
			sum = 0;
			for(i=3;i<header.length;i++)
				sum+=Double.parseDouble(rs.getString(i));
			sum = sum/5;
			if(sum>s)
				r++;
		}
		temp.addElement(r);
		cellData.add(temp);
		tableModel = new DefaultTableModel(cellData,columnNames);
		JTable table= new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return true;
	}
	
}

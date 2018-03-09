package StudentManageSystem;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.Vector;

public class GlobalSearchDialog implements ActionListener{
	JDialog s;
	
	JScrollPane scrollPane;
	JTable myTable;
	DefaultTableModel tableModel;
	
	JPanel pane;
	JButton add;
	JButton delete;
	JButton update;
	JButton sort;
	
	JPopupMenu pop;		//弹出右键
	JMenuItem addMenu;
	JMenuItem deleteMenu;
	JMenuItem updateMenu;
	JMenuItem sortMenu;
	
	Vector<Object> columnNames = new Vector<Object>();
	Vector<Vector<Object>> cellData = new Vector<Vector<Object>>();
	
	boolean sorted = false;
	
	public GlobalSearchDialog(JFrame frame)
	{
		s = new JDialog(frame,"成绩管理",true);
		pop = new JPopupMenu();
		addMenu = new JMenuItem("增加");
		addMenu.addActionListener(this);
		pop.add(addMenu);
		deleteMenu = new JMenuItem("删除");
		deleteMenu.addActionListener(this);
		pop.add(deleteMenu);
		updateMenu = new JMenuItem("修改");
		updateMenu.addActionListener(this);
		pop.add(updateMenu);
		sortMenu = new JMenuItem("排序");
		sortMenu.addActionListener(this);
		pop.add(sortMenu);
		
		try
		{
			String[] header={"ID","Name","数学","英语","语文","物理","生物","平均分"};
			for(int j=0;j<header.length;j++)
				columnNames.addElement(header[j]);
			@SuppressWarnings("unused")
			int row=0,col = columnNames.size();
			MySQL mysql = new MySQL();
			mysql.search("select * from studentmanager");
			ResultSet rs = mysql.getResultSet();
			while(rs.next())
			{
				double sum =0;
				int j;
				Vector<Object> temp = new Vector<Object>();
				for(j=1;j<=col-1;j++)
				{
					String s = rs.getString(j);//获取每一行的每一个元素
					temp.addElement(s);
					if(j>2)
					{
						sum += Double.parseDouble(s);
					}
				}
				temp.addElement(sum/5);
				cellData.addElement(temp);
				row++;
			}
			
			tableModel= new DefaultTableModel(cellData, columnNames);
			myTable = new JTable(tableModel);
			myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//关闭自动调整模式，超过则可以滚动
			myTable.addMouseListener(new MouseAdapter()//对每一行实施监听
			{
				public void mouseClicked(MouseEvent e)
				{
					if(e.getButton() == MouseEvent.BUTTON3)//右键
						pop.show(myTable,e.getX(),e.getY());
					else if(e.getButton() == MouseEvent.BUTTON1)//左键
					{
						delete.setEnabled(true);
						update.setEnabled(true);
					}
				}
			});	
		}catch(SQLException e2)
		{
			System.out.println("数据库语法错误");
			System.out.println(e2.toString());
		}
	}
	
	public void addScrollPane(Container dialogPane)
	{
		scrollPane = new JScrollPane(myTable);
		//myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				myTable.setFocusable(false);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
				delete.setEnabled(false);
				update.setEnabled(false);
			}
		});
		dialogPane.add(scrollPane,BorderLayout.NORTH);//可滚动的表格放在北面
	}
	
	public void addPane(Container dialogPane)
	{
		pane = new JPanel(new FlowLayout());
		add = new JButton("增加");
		delete = new JButton("删除");
		delete.setEnabled(false);
		update = new JButton("修改");
		update.setEnabled(false);
		sort = new JButton("排序");
		
		pane.add(add);add.addActionListener(this);
		pane.add(delete);delete.addActionListener(this);
		pane.add(update);update.addActionListener(this);
		pane.add(sort);sort.addActionListener(this);
		dialogPane.add(pane,BorderLayout.SOUTH);//将4个按键放在南
	}
	
	public void showDialog()
	{
		Container dialogPane = s.getContentPane();															//获取对话框面板
		s.setLayout(new BorderLayout());
		
		addScrollPane(dialogPane);
		addPane(dialogPane);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		
		s.setBounds(0,0,520, 520);
		int x = (width-s.getWidth())/2;
		int y = (height-s.getHeight())/2;
		s.setLocation(x,y);
		s.setVisible(true);
	}
	
	public void insertSort(Vector<Vector<Object>> cellData)
	{
		int i=0,j=0;
		int row = cellData.size()-1;
		int col= cellData.get(0).size()-1;
		Vector<Object> temp = cellData.get(row);
		for(j=row;j>=1;j--)
		{
			if(Double.parseDouble(temp.get(col-1).toString()) > Double.parseDouble(cellData.get(j-1).get(col-1).toString()))
			{
				cellData.set(j,cellData.get(j-1));
				int currentRank = Integer.parseInt(cellData.get(j).get(col).toString());
				cellData.get(j).set(col, ++currentRank);
			}
			else
				break;
		}
		cellData.set(j,temp);
		cellData.get(j).set(col,j+1);
		for(i=0;i<row;i++)
		{
			cellData.get(i).set(col,i+1);
		}
	}
	
	public void insertSort(Vector<Vector<Object>> cellData,int i)
	{
		if(sorted)
		{
			int row = cellData.size()-1;
			int col= cellData.get(0).size()-1;
			Vector<Object> temp = cellData.get(i);
			double updateNow = Double.parseDouble(temp.get(col-1).toString());
			if(i > 0 && updateNow > Double.parseDouble(cellData.get(i-1).get(col-1).toString()))
			{
				for(;i>0 && updateNow >  Double.parseDouble(cellData.get(i-1).get(col-1).toString());i--)
					cellData.set(i, cellData.get(i-1));
				cellData.set(i, temp);
			}
			else if(i < row && updateNow < Double.parseDouble(cellData.get(i+1).get(col-1).toString()))
			{
				for(;i<row && updateNow < Double.parseDouble(cellData.get(i+1).get(col-1).toString());i++)
					cellData.set(i,cellData.get(i+1));
				cellData.set(i, temp);
			}
			for(i=0;i<=row;i++)
				cellData.get(i).set(col,i+1);
		}
	}
	public void selectSort(Vector<Vector<Object>> cellData)
	{
		int i=0,j=0;
		if(!sorted)
		{
			int row = cellData.size();
			int col= cellData.get(0).size();
			Vector<Object> temp;
			for(i=0;i<row-1;i++)
			{
				for(j=i+1;j<row;j++)
					if(Double.parseDouble((cellData.get(j)).get(col-1).toString()) > Double.parseDouble(cellData.get(i).get(col-1).toString()))
					{
						temp = cellData.get(i);
						cellData.set(i,cellData.get(j));
						cellData.set(j, temp);
					}
				cellData.get(i).addElement(i+1);
			}
			cellData.get(i).addElement(i+1);
		}
	}
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("增加"))
		{
			if(!sorted)
			{
				cellData = new AddDialog(s,columnNames,cellData).getCellData();
				tableModel.setDataVector(cellData,columnNames);
				myTable.setModel(tableModel);
				tableModel.fireTableStructureChanged();
			}
			else
			{
				cellData = new AddDialog(s,columnNames,cellData).getCellData();
				int row =cellData.size();
				cellData.get(row-1).addElement(row);
				insertSort(cellData);
				tableModel.setDataVector(cellData,columnNames);
				myTable.setModel(tableModel);
				tableModel.fireTableStructureChanged();
			}
		}
		else if(e.getActionCommand().equals("删除"))
		{
			int row = myTable.getSelectedRow();
			if(row < 0)
				return ;
			String sql ="delete from studentmanager where ID = '"+tableModel.getValueAt(row, 0).toString()+"'";
			String str ="delete from users where userid='"+tableModel.getValueAt(row, 0).toString()+"'";
			MySQL mysql = new MySQL();
			if(mysql.delete(sql) != -1)
				JOptionPane.showMessageDialog(s,"删除成功");
			
			tableModel.removeRow(row);
			@SuppressWarnings("unused")
			int t = mysql.delete(str);
			if(sorted)
			{
				cellData = tableModel.getDataVector();
				insertSort(cellData);
				tableModel.setDataVector(cellData,columnNames);
			}
			tableModel.fireTableStructureChanged();
		}
		else if(e.getActionCommand().equals("修改"))
		{
			int row =myTable.getSelectedRow();
			if(row < 0)
				return ;
			int col = myTable.getSelectedColumn();
			int column = columnNames.size()-1;
			if(sorted)
				column = column - 1;
			double sum=0;
			String update = JOptionPane.showInputDialog("修改"+myTable.getValueAt(row,1)+"的"+columnNames.get(col)+"成绩");
			try{
				if(update.equals("")){}
			}catch(NullPointerException ex){
				JOptionPane.showMessageDialog(s,"未作任何修改！");
				return;
			}
			String sql;
			if(col == 0 || col == 1)
				sql = new String("update studentmanager set " + columnNames.get(col) +"='"+update+ "' where ID = '" + myTable.getValueAt(row, 0)+"'");
			else
				sql = new String("update studentmanager set " + columnNames.get(col) +"="+update+ " where ID = '" + myTable.getValueAt(row, 0)+"'");
			MySQL mysql = new MySQL();
			int flag = mysql.update(sql);
			if(flag == 1)
			{
				JOptionPane.showMessageDialog(s, "修改成功！");
				tableModel.setValueAt(update, row, col);
				for(int i=2;i<column;i++)
					sum+=Double.parseDouble(myTable.getValueAt(row, i).toString());
				sum/=5;
				tableModel.setValueAt(sum, row, column);
				if(sorted)
				{
					cellData = tableModel.getDataVector();
					insertSort(cellData,row);
					tableModel.setDataVector(cellData, columnNames);
						
				}
				tableModel.fireTableStructureChanged();
			}
		}
		else if(e.getActionCommand().equals("排序"))
		{
			if(!sorted)
			{
				columnNames.addElement("排名");
				selectSort(cellData);
				sorted = true;
			}
			else
				insertSort(cellData);
			tableModel.setDataVector(cellData, columnNames);
			myTable.setModel(tableModel);
			tableModel.fireTableStructureChanged();
		}
	}
}

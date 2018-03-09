package StudentManageSystem;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class AddDialog implements ActionListener{
	JDialog addDialog;
	JDialog parentFrame;
	
	JLabel label[];
	JTextField input[];
	
	JButton yes;
	JButton no;
	
	Vector<Object> addItem = new Vector<Object>();
	Vector<Object> columnNames;
	Vector<Vector<Object>> cellData;
	
	final int ROWS = 7;
	
	@SuppressWarnings("deprecation")
	public AddDialog(JDialog dialog,Vector<Object> columnNames,Vector<Vector<Object>> cellData)
	{
		addDialog = new JDialog(dialog,"增加信息",true);
		this.cellData = cellData;
		this.columnNames = columnNames;
		Container dialogPane = addDialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		JPanel pane1 = new JPanel(new GridLayout(7,2));

		int i;
		int col = ROWS;
		label = new JLabel[col];
		input = new JTextField[col];
		for(i = 0;i<ROWS;i++)
		{
			label[i] = new JLabel(columnNames.get(i).toString());
			input[i] = new JTextField(4);
			pane1.add(label[i]);
			pane1.add(input[i]);
		}
		dialogPane.add(pane1,BorderLayout.NORTH);
		
		yes = new JButton("确定");
		no = new JButton("取消");
		JPanel pane2 = new JPanel(new FlowLayout());
		pane2.add(yes);
		yes.addActionListener(this);
		pane2.add(no);
		no.addActionListener(this);
		dialogPane.add(pane2,BorderLayout.SOUTH);
		
		addDialog.setBounds(0,0,200,300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width-addDialog.getWidth())/2;
		int y = (height-addDialog.getHeight())/2;
		addDialog.setLocation(x,y);
		addDialog.setVisible(true);
	}
				
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("确定"))
		{
			boolean haveNull = false;
			String[] recieve = new String[ROWS];
			for(int i=0;i<ROWS;i++)
			{
				recieve[i] = input[i].getText();
				if(recieve[i].equals(""))
				{
					haveNull = true;
					break;
				}
			}
			if(haveNull)
			{
				JOptionPane.showMessageDialog(addDialog,"您输入数据不合法，不能留空，请重新输入！");
				return ;
			}
			MySQL mysql = new MySQL();
			try
			{
				double sum =0;
				for(int j = 0;j<ROWS;j++)
					if(j>=2)
						sum+=Double.parseDouble(recieve[j].toString());
				sum/=5;
				
				if(mysql.insert("insert into studentmanager values("+"'"+recieve[0]+"','"+recieve[1]+"',"+recieve[2]+","+recieve[3]+","+recieve[4]+","+recieve[5]+","+recieve[6]+")") == -1)
				{
					
					addDialog.dispose();
					return;
				}
				@SuppressWarnings("unused")
				int t = mysql.insert("insert into users values('"+recieve[0]+"','"+recieve[1]+"','"+recieve[0]+"','"+null+"','"+null+"','"+null+"')");
				JOptionPane.showMessageDialog(addDialog,"添加数据成功");
				for(int j = 0;j<ROWS;j++)
					addItem.addElement(recieve[j]);
				addItem.addElement(sum);
				cellData.addElement(addItem);
				addDialog.dispose();
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(addDialog, ex.toString()+"\n输入有误！请重新输入");
			}			
		}
		else if(e.getSource() == no)
		{
			addDialog.dispose();
		}
	}
	public Vector<Vector<Object>> getCellData()
	{
		return cellData;
	}
}

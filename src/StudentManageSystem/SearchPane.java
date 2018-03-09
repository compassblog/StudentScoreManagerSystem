package StudentManageSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SearchPane extends JPanel implements ActionListener{
	JPanel globalPane;
	JButton gsearch;
	
	JPanel localPane;
	JRadioButton sByID;
	JRadioButton sByName;
	JTextField sText;
	JButton lsearch;
	
	JFrame ownedFrame;
	int searchMode = 1;
	public SearchPane(JFrame f)
	{
		this.ownedFrame = f;
		setLayout(new CardLayout());
		globalPane = new JPanel();
		gsearch = new JButton("一键查询管理");	gsearch.addActionListener(this);
		globalPane.add(gsearch);

		localPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lsearch = new JButton("直接查询");				lsearch.addActionListener(this);	
		sByID = new JRadioButton ("按学号查询");		sByID.addActionListener(this);
		sByID.setSelected(true);
		sByName = new JRadioButton("按姓名查询");	sByName.addActionListener(this);
		sText = new JTextField();					sText.addActionListener(this);
		sText.setColumns(20);
		
		localPane.add(sByID);
		localPane.add(sByName);
		localPane.add(sText);
		localPane.add(lsearch);
		
		add(globalPane,"全局查询管理");
		add(localPane,"局部查询管理");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == gsearch)
			new GlobalSearchDialog(ownedFrame).showDialog();//showDialog方法可以对窗口的大小方向做调整
		else if(e.getSource() == lsearch || e.getSource() == sText)
		{
			String inputInfo = sText.getText();
			if(inputInfo.equals(""))
			{
				JOptionPane.showMessageDialog(ownedFrame,"请输入您要查询的基本信息");
				return;
			}
			new LocalSearchDialog(ownedFrame).showInfo(sText.getText(),searchMode);
		}
		else if(e.getSource() == sByID)
		{
			sByName.setSelected(false);
			searchMode = 1;
		}
		else if(e.getSource() == sByName)
		{
			sByID.setSelected(false);
			searchMode = 0;
		}
	}
}

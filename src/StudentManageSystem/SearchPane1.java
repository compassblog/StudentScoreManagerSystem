package StudentManageSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SearchPane1 extends JPanel implements ActionListener{
	//JPanel globalPane;
	//JButton gsearch;
	
	JPanel localPane;
	JRadioButton sByID;
	JRadioButton sByName;
	JTextField sText;
	JButton lsearch;
	
	JFrame ownedFrame;
	int searchMode = 1;
	public SearchPane1(JFrame f)
	{
		this.ownedFrame = f;
		setLayout(new CardLayout());

		localPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lsearch = new JButton("查询");				lsearch.addActionListener(this);	
		sByID = new JRadioButton ("按本人学号查询");		sByID.addActionListener(this);
		sByID.setSelected(true);
		sByName = new JRadioButton("按本人姓名查询");	sByName.addActionListener(this);
		sText = new JTextField();					sText.addActionListener(this);
		sText.setColumns(20);
		
		localPane.add(sByID);
		localPane.add(sByName);
		localPane.add(sText);
		localPane.add(lsearch);
		add(localPane,"按学号姓名查询");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == lsearch || e.getSource() == sText)
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

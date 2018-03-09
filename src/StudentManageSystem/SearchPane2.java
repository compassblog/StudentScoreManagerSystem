package StudentManageSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import DataBase.DBcon;
import javax.swing.*;

@SuppressWarnings("serial")
public class SearchPane2 extends JPanel implements ActionListener{
	JPanel globalPane;
	JButton gsearch;
	
	JFrame ownedFrame;
	int searchMode = 1;
	String a;
	public SearchPane2(JFrame f,String s)
	{
		a=s;
		this.ownedFrame = f;
		setLayout(new CardLayout());
		globalPane = new JPanel();
		gsearch = new JButton("一键查询管理");	gsearch.addActionListener(this);
		globalPane.add(gsearch);	
		add(globalPane,"一键查询管理");
		//add(localPane,"局部查询管理");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == gsearch){
			try{
				DBcon db = new DBcon();
				ResultSet rs = db.query("select type from users where userid='"+a+"'");
				rs.next();
				int r = Integer.parseInt(rs.getString("type"));
				if(r == 0){
					new GlobalSearchDialog1(ownedFrame,a).showDialog();//showDialog方法可以对窗口的大小方向做调整
				}else{
					//JOptionPane.showMessageDialog(null, "成绩提交成功，您不可以再修改学生成绩！");
					//new GlobalSearchDialogT(ownedFrame,a);//.showDialog();
					new GlobalSearchDialogT(ownedFrame,a).showDialog();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			
		}
	}
}

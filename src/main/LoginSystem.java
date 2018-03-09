package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



import DataBase.*;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;



@SuppressWarnings("serial")
public class LoginSystem extends JFrame implements ActionListener,ItemListener{
	JPanel panel2,jpanel;
	JLabel UserType,UserLabel,PasswordLabel;
	JTextField UserTextField;
	JPasswordField PasswordTextField;
	JButton Yes,Cancel,Exit;
	@SuppressWarnings("rawtypes")
	JComboBox UserSelect;
	String user[]={"","学生用户","教师用户","管理员"};
	
	
	Container c;
	int i;
	Image image=new ImageIcon("src/main/123.jpg").getImage();
	ImageIcon labIma=new ImageIcon("src/main/123.jpg");
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	LoginSystem(){
		super("用户登录");
		jpanel=new MyPanel(image);
		jpanel=new MyPanel(image);
		
		
		UserType=new JLabel("用户类型",JLabel.CENTER);
		UserSelect=new JComboBox(user);
		UserSelect.addItemListener(this);
		UserLabel=new JLabel("用户名",JLabel.CENTER);
		PasswordLabel=new JLabel("密码",JLabel.CENTER);		
		
		UserTextField=new JTextField(10);
		PasswordTextField=new JPasswordField(10);
		
		Yes=new JButton("确定");
		Cancel=new JButton("取消");
		Exit=new JButton("退出");
		
		Yes.addActionListener(this);
		Cancel.addActionListener(this);
		Exit.addActionListener(this);
              
		jpanel.setLayout(new GridLayout(3,2,2,50));
		panel2=new JPanel();
		c=getContentPane();
		c.setLayout(new BorderLayout());
				
		jpanel.add(UserType);
		jpanel.add(UserSelect);
		jpanel.add(UserLabel);
		jpanel.add(UserTextField);
		jpanel.add(PasswordLabel);
		jpanel.add(PasswordTextField);
		  
		 c.add(jpanel,BorderLayout.CENTER);
		panel2.add(Yes);
		panel2.add(Cancel);
		panel2.add(Exit);
		
		c.add(panel2, BorderLayout.SOUTH);	
		setBounds(300,300,labIma.getIconWidth(),labIma.getIconHeight());
		setVisible(true);	
	}

	public void itemStateChanged(ItemEvent e)
	{
		i=UserSelect.getSelectedIndex();
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==Cancel)
		{
			UserTextField.setText("");
			PasswordTextField.setText("");
			return;
			
		}
		if(e.getSource()==Exit)
		{
			System.exit(0);
		}
		else
		{
			if(UserTextField.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				return;
			}
			if(PasswordTextField.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return;
			}
			boolean islogin=false;
			switch(i)
			{
			case 1:
				if(Service.getUserType(UserTextField.getText().trim(),PasswordTextField.getText().trim())>0)
				{
					//JOptionPane.showMessageDialog(null, "学生登陆成功");
					try {
						new StudentFrame(UserTextField.getText().trim());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					islogin=true;	
					
				}break;
			case 2:
				if(Service.getUserType(UserTextField.getText().trim(),PasswordTextField.getText().trim())>0)
				{
					//JOptionPane.showMessageDialog(null, "教师登陆成功");
					new TeacherFrame(UserTextField.getText().trim());
					dispose();
					islogin=true;
				}break;
			case 3:
				if(Service.getAdminId(UserTextField.getText().trim(),PasswordTextField.getText().trim())>0)
				{
					//JOptionPane.showMessageDialog(null, "管理员登陆成功");
					new MainFrame();
					dispose();
					islogin=true;	
				}break;
			default:
				break;
			  }
			if(!islogin){
			JOptionPane.showMessageDialog(null, "用户名错误或者密码不正确");
			}
		}
			
	}
	public static void main(String args[]){
	    try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
//            SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
//          SubstanceLookAndFeel.setSkin(new EmeraldDuskSkin());
//          SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
          SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
//          SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
//            SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
//            SubstanceLookAndFeel.setCurrentTitlePainter(new FlatTitePainter());
       // 设置边框
          SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
       // 设置按钮外观
          SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper()); 
          
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }

		@SuppressWarnings("unused")
		LoginSystem mainFrame=new LoginSystem();
	}
 
}
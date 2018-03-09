package StudentManageSystem;
import java.util.*;

public class Student {
	private String ID;
	private String Name;
	private Vector<String> Course;
	private Vector<Double> Score;
	
	public Student(String ID,String Name)
	{
		this.ID = ID;
		this.Name = Name;
		
		Course.addElement("Math");
		Course.addElement("English");
		Course.addElement("Java");
		Course.addElement("OS");
		Course.addElement("PE");
	}
	
	String getID()
	{
		return ID;
	}
	
	String getName()
	{
		return Name;
	}
	
	Vector<String> getCourse()
	{
		return Course;
	}
	
	Vector<Double> getScore()
	{
		return Score;
	}
	
	void setScore(Vector<Double> s)
	{
		Score = s;
	}
}


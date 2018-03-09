package Users;

public class User {
	public  String userNo;//用户ID号（系统内唯一）
	public String name;//用户姓名
	public String sexy;//性别
	public String classGrade;//班级
	public String coursename;//课程名称
	public String score;//课程分数;
	public String item;//学期
	public User(String userNo,String name,String sexy,String classGrade,String coursename,String score,String item){
		this.userNo = userNo;
		this.name = name;
		this.sexy = sexy;
		this.classGrade = classGrade;
		this.coursename = coursename;
		this.score = score;
		this.item = item;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getItem(){
		return item;
	}
	public void setItem(String item){
		this.item = item;
	}
	public String getSexy(){
		return sexy;
	}
	public void setSexy(String sexy){
		this.sexy = sexy;
	}
	public String getClassGrade(){
		return classGrade;
	}
	public void setClassGrade(String classGrade){
		this.classGrade = classGrade;
	}

	
}

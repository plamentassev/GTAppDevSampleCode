package edu.gatech.seclass.project3;

public class Student {
	private String name;
	private String team;
	private int attendance;
	private int gtid;
	private String email;
	private Course course;
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Student(){}
	
	public Student(String name, int gtid, String email){
		this.name = name;
		this.email = email;
		this.gtid = gtid;
	}
	
	public Student(String name, int gtid, Course course) {
		this.name = name;
		this.gtid = gtid;
		this.course= course;
	}
	
	public Student(String name, int gtid) {
		this.name = name;
		this.gtid = gtid;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	public int getAttendance() {
		return attendance;
	}
	
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	
	public int getGtid() {
		return gtid;
	}
	public void setGtid(int gtid) {
		this.gtid = gtid;
	}
}

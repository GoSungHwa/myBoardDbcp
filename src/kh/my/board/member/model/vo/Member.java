package kh.my.board.member.model.vo;

import java.sql.Date;

//----------- -------- ------------- 
//MEMBER_ID   NOT NULL VARCHAR2(30)  
//MEMBER_PWD  NOT NULL VARCHAR2(30)  
//MEMBER_NAME NOT NULL VARCHAR2(30)  
//GENDER               CHAR(1)       
//EMAIL                VARCHAR2(50)  
//PHONE                VARCHAR2(30)  
//ADDRESS              VARCHAR2(100) 
//AGE                  NUMBER        
//ENROLL_DATE          DATE

// TODO:
// 한땀한땀 작성
// getter // settter
// toString  손으로 작성
// constructor (full 인자)
public class Member {
	private String member_id;
	private String member_pwd;
	private String member_name;
	private char gender;   // vo -char, db-char(1)
	private String email;
	private String phone;
	private String address;
	private int age;
	private Date enroll_date;
	private int point;

	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(String member_id, String member_pwd, String member_name, char gender, String email, String phone,
			String address, int age, Date enroll_date) {
		super();
		this.member_id = member_id;
		this.member_pwd = member_pwd;
		this.member_name = member_name;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.age = age;
		this.enroll_date = enroll_date;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", member_pwd=" + member_pwd + ", member_name=" + member_name
				+ ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", address=" + address + ", age="
				+ age + ", enroll_date=" + enroll_date+ ", point=" + point + "]";
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pwd() {
		return member_pwd;
	}

	public void setMember_pwd(String member_pwd) {
		this.member_pwd = member_pwd;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getEnroll_date() {
		return enroll_date;
	}

	public void setEnroll_date(Date enroll_date) {
		this.enroll_date = enroll_date;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}

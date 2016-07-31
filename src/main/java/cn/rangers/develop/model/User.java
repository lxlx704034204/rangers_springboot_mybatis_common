package cn.rangers.develop.model;

import cn.rangers.develop.common.BaseEntity;

public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer age;
	
	private String password;


	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getAge() {
		return age;
	}

	
	public void setAge(Integer age) {
		this.age = age;
	}

	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}

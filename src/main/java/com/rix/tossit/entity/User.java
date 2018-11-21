package com.rix.tossit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="user")
public class User {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long userId; 
	
	@NotNull
	@Column
	private String userName;

	@NotNull
	@Column
	private String pwd;
	
	public long getId() {
		return userId;
	}

	public void setId(long id) {
		this.userId = id;
	}

	public User() {}
	
	public User(String userName, String pwd) {
		this.userName = userName;
		this.pwd = pwd;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	};
	
	

}

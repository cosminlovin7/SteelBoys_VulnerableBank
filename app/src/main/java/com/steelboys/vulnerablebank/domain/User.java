package com.steelboys.vulnerablebank.domain;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String phone_no;
	private Integer balance;

	public User() {

	}

	public User(
		final Integer id,
		final String username,
		final String password,
		final String first_name,
		final String last_name,
		final String phone_no,
		final Integer balance
	) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_no = phone_no;
		this.balance = balance;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public Integer getBalance() {
		return balance;
	}
}

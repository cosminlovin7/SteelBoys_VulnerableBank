package com.steelboys.vulnerablebank.domain;

public class User {
	private Integer id;
	private String username;
	private String password;

	public User() {

	}

	public User(
		final Integer id,
		final String username,
		final String password
	) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
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
}

package com.steelboys.vulnerablebank.requests;

public class RequestLoginObj {
	private String username;
	private String password;

	public RequestLoginObj() {
		super();
	}

	public RequestLoginObj(
		final String username,
		final String password
	) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}

package com.steelboys.vulnerablebank.dto;

public class AccountInfoDto {
	public String firstName;
	public String lastName;
	public String email;
	public Integer balance;

	public AccountInfoDto() {
		super();
	}

	public AccountInfoDto(
		String firstName,
		String lastName,
		String email,
		Integer balance
	) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.balance = balance;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Integer getBalance() {
		return balance;
	}
}


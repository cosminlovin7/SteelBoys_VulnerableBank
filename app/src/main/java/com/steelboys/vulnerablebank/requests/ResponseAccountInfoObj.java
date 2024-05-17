package com.steelboys.vulnerablebank.requests;

import com.steelboys.vulnerablebank.dto.AccountInfoDto;

public class ResponseAccountInfoObj {
	public int status;
	public String message;
	public AccountInfoDto accountInfo;

	public ResponseAccountInfoObj() {
		super();
	}

	public ResponseAccountInfoObj(
		int status,
		String message,
		AccountInfoDto accountInfo
	) {
		super();
		this.status = status;
		this.message = message;
		this.accountInfo = accountInfo;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public AccountInfoDto getAccountInfo() {
		return accountInfo;
	}
}
package com.steelboys.vulnerablebank.requests;

public class ResponseLoginObj {
	private int status;
	private String message;

	public ResponseLoginObj() {
		super();
	}

	public ResponseLoginObj(
		final int status,
		final String message
	) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ResponseLogin{" +
			"status=" + status +
			", message='" + message + '\'' +
			'}';
	}
}

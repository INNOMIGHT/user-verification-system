package com.mini_assignment.user_verification.dto;

import java.util.Date;

import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

public class ErrorResponse extends ResponseEntityExceptionHandler {

	private static final long serialVersionUID = -3985143863391705694L;
	private String errorMessage;
	private int errorCode;
	private Date timestamp;

	public ErrorResponse(String errorMessage, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.timestamp = new Date();
	}
	public ErrorResponse() {
		super();
	}


	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



}

package com.todo.exception;

public class ErrorSubDetail {

	public ErrorSubCode errorSubCode = ErrorSubCode.Internal_server_error;
	public String errorDetail = "";

	public ErrorSubDetail() {
		super();
	}

	public ErrorSubDetail(ErrorSubCode errorSubCode, String errorDetail) {
		super();
		this.errorSubCode = errorSubCode;
		this.errorDetail = errorDetail;
	}

	public ErrorSubCode getErrorSubCode() {
		return errorSubCode;
	}

	public void setErrorSubCode(ErrorSubCode prdOffErrorSubCode) {
		this.errorSubCode = prdOffErrorSubCode;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}

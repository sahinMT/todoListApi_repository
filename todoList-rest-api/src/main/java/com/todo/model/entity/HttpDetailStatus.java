package com.todo.model.entity;

public enum HttpDetailStatus {
	Invalid_URL(10, "Invalid URL parameter value"),
	Missing_body(11, "Missing body"),
	Invalid_body(12, "Invalid body"),
	Missing_body_field(13, "Missing body field"),
	Invalid_body_field(14, "Invalid body field"),
	Missing_header(15, "Missing header"),
	Invalid_header_value(16, "Invalid header value"),
	Missing_query_string_parameter(17, "Missing query-string parameter"),
	Invalid_query_string_parameter_value(18, "Invalid query-string parameter value"),
	Resource_not_found(19,"Resource not found"),
	Method_not_allowed(20,"Method not allowed"),
	Internal_error(21, "Internal_server_error"),
	Missing_request_param(22, "Missing request parameter"),
	Not_found_content(23, "Not found any data");

	private final int value;

	private final String reasonPhrase;

	HttpDetailStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}
	
	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(this.value) + " " + name();
	}

}

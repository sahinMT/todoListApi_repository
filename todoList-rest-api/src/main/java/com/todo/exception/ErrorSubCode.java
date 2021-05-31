package com.todo.exception;

public enum ErrorSubCode {
	Conflict_List_name(1, "ToDoList 'name' does not exists in the system"),
	Not_found_list_name_in_system(2, "ToDoList 'name' does not exists in the system"),
	Invalid_or_missing_request_body(3, "Invalid or missing request body"),
	Invalid_or_missing_request_param(4, "Not found request param.."),
	Invalid_or_missing_name_field(5, "Invalid or missing ProductOffering name field"),
	Not_found_data(6, "Not found data.."),
	Not_found_user(6, "Not found user.."),
	Internal_server_error(10, "Internal Server Error");

	private final int code;

	private final String reasonPhrase;

	ErrorSubCode(int code, String reasonPhrase) {
		this.code = code;
		this.reasonPhrase = reasonPhrase;
	}

	public int getCode() {
		return code;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	@Override
	public String toString() {
		return Integer.toString(this.code) + " " + name();
	}
}

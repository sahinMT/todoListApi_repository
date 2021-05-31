package com.todo.model.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListItemStatus {

	C('C'), O('O');

	private char value;

	ListItemStatus(char value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

}

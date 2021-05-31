package com.todo.model.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListStatus {
	O('O'), C('C');

	private char value;

	ListStatus(char value) {
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

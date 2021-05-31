package com.todo.model.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
	A('A'), D('D');

	private char value;

	UserStatus(char value) {
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

package com.todo.model.dto;

import java.sql.Timestamp;

import com.todo.model.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Long id;
	private String name;
	private UserStatus status;
	private Timestamp registerDate;
}

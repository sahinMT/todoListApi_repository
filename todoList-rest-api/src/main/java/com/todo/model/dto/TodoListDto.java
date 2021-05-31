package com.todo.model.dto;

import java.sql.Timestamp;
import java.util.List;

import com.todo.model.entity.ListStatus;
import com.todo.model.entity.User;

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
public class TodoListDto {

	private long id;
	private String name;
	private List<ListItemDto> items;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String description = null;
	private ListStatus status;
	private User user;
}

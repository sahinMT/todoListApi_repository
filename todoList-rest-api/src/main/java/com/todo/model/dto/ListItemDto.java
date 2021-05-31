package com.todo.model.dto;

import java.sql.Timestamp;

import com.todo.model.entity.ListItemStatus;

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
public class ListItemDto {

	private long id;
	private String name;
	private ListItemStatus status;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String description = null;
	private TodoListDto list;

}

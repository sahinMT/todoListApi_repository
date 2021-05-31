package com.todo.service;

import java.util.List;

import com.todo.model.dto.TodoListDto;

public interface ListService {

	TodoListDto save(TodoListDto listDto);

	boolean deleteWithListName(String name,long userId);

	TodoListDto getAllWithListName(String listName,long userId);

	List<TodoListDto> getAllWithId(long userId);

	boolean isListExists(String name, long userId);
}

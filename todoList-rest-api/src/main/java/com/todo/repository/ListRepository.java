package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.entity.TodoList;

public interface ListRepository extends JpaRepository<TodoList, Long> {
	TodoList findByNameAndUserId(String name,long userId);

	TodoList findByIdAndUserId(long id,long userId);

	boolean existsByNameAndUserId(String name, long userId);

	List<TodoList> findByUserId(long username);

}

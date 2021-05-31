package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.entity.ListItem;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {
	ListItem findByNameAndListId(String name, long listId);

	boolean existsByNameAndListId(String itemName, long listId);

	ListItem findByName(String name);

}

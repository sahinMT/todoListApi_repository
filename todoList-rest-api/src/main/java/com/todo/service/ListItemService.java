package com.todo.service;

import com.todo.model.dto.ListItemDto;

public interface ListItemService {

	ListItemDto save(ListItemDto listItemDto);

	boolean deleteWithListItemName(String listItemName, long listId, long userId);

	ListItemDto getListItem(String name,long listId);

	boolean isListItemExists(String itemName, long listId, long userId);
	
	ListItemDto findByName(String listItemName);
}

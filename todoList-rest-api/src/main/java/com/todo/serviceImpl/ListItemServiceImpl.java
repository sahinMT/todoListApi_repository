package com.todo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.dto.ListItemDto;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.ListItem;
import com.todo.model.entity.TodoList;
import com.todo.repository.ListItemRepository;
import com.todo.repository.ListRepository;
import com.todo.service.ListItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListItemServiceImpl implements ListItemService {

	@Autowired
	private ListRepository listRepository;

	@Autowired
	private ListItemRepository listItemRepository;

	@Override
	public ListItemDto save(ListItemDto listItemDto) {
		ListItem item = new ListItem();
		item.setCdate(listItemDto.getCreateDate());
		item.setDescription(listItemDto.getDescription());
		item.setName(listItemDto.getName());
		item.setStatus(listItemDto.getStatus());
		item.setUdate(listItemDto.getUpdateDate());

		TodoList todoList = new TodoList();
		todoList.setName(listItemDto.getList().getName());
		todoList.setCdate(listItemDto.getList().getCreateDate());
		todoList.setStatus(listItemDto.getList().getStatus());
		item.setList(todoList);
//		item.setList(listItemDto.getList());

		ListItem savedListItem = listItemRepository.save(item);
		listItemDto.setId(savedListItem.getId());
		return listItemDto;
	}

	@Override
	public boolean deleteWithListItemName(String name, long listId, long userId) {
		TodoList list = listRepository.findByIdAndUserId(listId, userId);
		if (list == null)
			return false;

		ListItem listItem = listItemRepository.findByNameAndListId(name, listId);

		if (listItem != null) {
			listItemRepository.deleteById(listItem.getId());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ListItemDto getListItem(String listItemName, long listId) {

		ListItem todoListItem = listItemRepository.findByNameAndListId(listItemName, listId);

		ListItemDto todoListItemDto = new ListItemDto();
		todoListItemDto.setId(todoListItem.getId());
		todoListItemDto.setName(todoListItem.getName());
		todoListItemDto.setCreateDate(todoListItem.getCdate());
		todoListItemDto.setStatus(todoListItem.getStatus());
		todoListItemDto.setUpdateDate(todoListItem.getUdate());
		todoListItemDto.setDescription(todoListItem.getDescription());

		return todoListItemDto;
	}

	@Override
	public boolean isListItemExists(String itemName, long listId, long userId) {
		TodoList list = listRepository.findByIdAndUserId(listId, userId);
		if (list == null)
			return true;

		boolean isListExists = listItemRepository.existsByNameAndListId(itemName, listId);
		return isListExists;

	}

	@Override
	public ListItemDto findByName(String listItemName) {
		ListItem todoListItem = listItemRepository.findByName(listItemName);

		ListItemDto todoListItemDto = new ListItemDto();
		todoListItemDto.setId(todoListItem.getId());
		todoListItemDto.setName(todoListItem.getName());
		todoListItemDto.setCreateDate(todoListItem.getCdate());
		todoListItemDto.setStatus(todoListItem.getStatus());
		todoListItemDto.setUpdateDate(todoListItem.getUdate());
		todoListItemDto.setDescription(todoListItem.getDescription());

		return todoListItemDto;
	}
}

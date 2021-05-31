package com.todo.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.model.dto.ListItemDto;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.ListItem;
import com.todo.model.entity.ListItemStatus;
import com.todo.model.entity.ListStatus;
import com.todo.model.entity.TodoList;
import com.todo.repository.ListItemRepository;
import com.todo.repository.ListRepository;
import com.todo.service.ListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService {

	@Autowired
	private ListRepository listRepository;

	@Autowired
	private ListItemRepository listItemRepository;

	@Override
	@Transactional
	public TodoListDto save(TodoListDto listDto) {
		TodoList list = new TodoList();
		list.setName(listDto.getName());
		list.setStatus(ListStatus.O);
		list.setDescription(listDto.getDescription());
		list.setCdate(new Timestamp(System.currentTimeMillis()));
		list.setUdate(new Timestamp(System.currentTimeMillis()));
		list.setUser(listDto.getUser());
		TodoList savedList = listRepository.save(list);
		listDto.setId(savedList.getId());

		List<ListItem> itemList = new ArrayList<ListItem>();

		listDto.getItems().forEach(item -> {
			ListItem listItem = new ListItem();
			listItem.setList(savedList);
			listItem.setName(item.getName());
			listItem.setStatus(ListItemStatus.O);
			listItem.setDescription(item.getDescription());
			listItem.setCdate(new Timestamp(System.currentTimeMillis()));
			listItem.setUdate(new Timestamp(System.currentTimeMillis()));
			itemList.add(listItem);
			listItemRepository.save(listItem);
		});
		list.setItems(itemList);

		return listDto;
	}

	public boolean isListExists(String name, long userId) {
		boolean isListExists = listRepository.existsByNameAndUserId(name, userId);
		if (isListExists) {
			return true;
		}
		return false;

	}

	@Override
	public boolean deleteWithListName(String listName, long userId) {
		TodoList list = listRepository.findByNameAndUserId(listName, userId);

		if (list != null) {
			listRepository.deleteById(list.getId());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<TodoListDto> getAllWithId(long userId) {

		List<TodoList> todoLists = listRepository.findByUserId(userId);
		List<TodoListDto> listDto = new ArrayList<>();

		todoLists.forEach(element -> {
			TodoListDto todoListDto = new TodoListDto();
			todoListDto.setName(element.getName());
			todoListDto.setCreateDate(element.getCdate());
			todoListDto.setStatus(element.getStatus());

			List<ListItemDto> itemDtoList = new ArrayList<>();
			for (ListItem item : element.getItems()) {
				ListItemDto listItemDto = new ListItemDto();
				listItemDto.setName(item.getName());
				listItemDto.setStatus(item.getStatus());
				listItemDto.setCreateDate(item.getCdate());
				listItemDto.setUpdateDate(item.getUdate());
				itemDtoList.add(listItemDto);
			}

			todoListDto.setItems(itemDtoList);
			todoListDto.setUpdateDate(element.getUdate());
			listDto.add(todoListDto);
		});

		return listDto;
	}

	@Override
	public TodoListDto getAllWithListName(String listName, long userId) {
		TodoList todoList = listRepository.findByNameAndUserId(listName, userId);

		if (todoList != null) {
			TodoListDto todoListDto = new TodoListDto();
			todoListDto.setName(todoList.getName());
			todoListDto.setCreateDate(todoList.getCdate());
			todoListDto.setStatus(todoList.getStatus());
			todoListDto.setUser(todoList.getUser());
			todoListDto.setId(todoList.getId());

			List<ListItemDto> itemDtoList = new ArrayList<>();
			for (ListItem item : todoList.getItems()) {
				ListItemDto listItemDto = new ListItemDto();
				listItemDto.setName(item.getName());
				listItemDto.setStatus(item.getStatus());
				listItemDto.setCreateDate(item.getCdate());
				listItemDto.setUpdateDate(item.getUdate());
				itemDtoList.add(listItemDto);
			}

			todoListDto.setItems(itemDtoList);
			todoListDto.setUpdateDate(todoList.getUdate());
			return todoListDto;
		}
		return null;
	}

}

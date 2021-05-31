package com.todo.service;

import java.util.List;

import com.todo.model.dto.UserDto;
import com.todo.model.entity.User;

public interface UserService {

	User getUserWithName(String username);
	
	List<UserDto> getAll();
	
	boolean isUserExists(String username);
	
	UserDto save(UserDto userDto);
	
	boolean deleteWithUsername(String username);
}

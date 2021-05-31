package com.todo.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.dto.UserDto;
import com.todo.model.entity.User;
import com.todo.model.entity.UserStatus;
import com.todo.repository.UserRepository;
import com.todo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserWithName(String username) {
		User user = userRepository.findByName(username);
		return user;
	}

	@Override
	public List<UserDto> getAll() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<UserDto>();

		users.forEach(user -> {
			UserDto userDto = new UserDto();
			userDto.setName(user.getName());
			userDto.setStatus(user.getStatus());
			userDto.setRegisterDate(user.getRegisterDate());
			userDto.setId(user.getId());

			userDtoList.add(userDto);
		});
		return userDtoList;
	}

	@Override
	public boolean isUserExists(String username) {
		boolean isListExists = userRepository.existsByName(username);

		if (isListExists) {
			return true;
		}
		return false;
	}

	@Override
	public UserDto save(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		user.setStatus(UserStatus.A);

		User userSaved = userRepository.save(user);
		userDto.setId(userSaved.getId());
		return userDto;
	}

	@Override
	public boolean deleteWithUsername(String username) {
		User user = userRepository.findByName(username);

		if (user != null) {
			userRepository.deleteById(user.getId());
			return true;
		} else {
			return false;
		}
	}

}

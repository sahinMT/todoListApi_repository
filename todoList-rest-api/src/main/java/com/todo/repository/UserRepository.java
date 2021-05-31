package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String username);
	
	boolean existsByName(String name);

}

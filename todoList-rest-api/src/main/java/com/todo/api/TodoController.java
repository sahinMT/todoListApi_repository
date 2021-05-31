package com.todo.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

public class TodoController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}

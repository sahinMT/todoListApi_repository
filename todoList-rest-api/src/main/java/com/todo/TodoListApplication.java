package com.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

	public static void main(String[] args) {
//      System.setProperty("server.servlet.context-path", "/");
		SpringApplication.run(TodoListApplication.class, args);
//		System.out.println(System.getProperty("server.servlet.context-path"));
	}

}

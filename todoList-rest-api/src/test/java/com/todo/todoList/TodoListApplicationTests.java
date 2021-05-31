package com.todo.todoList;

import static org.assertj.core.api.BDDAssertions.then;

import java.net.URL;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.todo.TodoListApplication;

@SpringBootTest(classes = TodoListApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
class TodoListApplicationTests {

	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/list");
	}

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		System.out.println("Test Response: " + response.getBody()); 
	}
	
	@Test
	  public void getListTest() throws Exception {
	    @SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.template.getForEntity(
	        "http://localhost:" + this.port + "/list/getList?name=ReadBook&username=User1", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	  }
	
	@Test
	  public void getUsersTest() throws Exception {
	    @SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.template.getForEntity(
	        "http://localhost:" + this.port + "/user/getUsers", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	  }


	  @Test
	  public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
	    @SuppressWarnings("rawtypes")
	    ResponseEntity<Map> entity = this.template.getForEntity(
	        "http://localhost:" + this.port + "/actuator/info", Map.class);

	    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	  }

}

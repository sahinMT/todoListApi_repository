package com.todo.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.exception.ApiException;
import com.todo.exception.Error;
import com.todo.exception.ErrorSubCode;
import com.todo.exception.ErrorSubDetail;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.HttpDetailStatus;
import com.todo.model.entity.User;
import com.todo.service.ListService;
import com.todo.service.UserService;

@RestController
public class ListApiController implements ListApi {
	private static final Logger log = LoggerFactory.getLogger(ListApiController.class);
	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;

	@Autowired
	private ListService listService;

	@Autowired
	private UserService userService;

	@Autowired
	public ListApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public String index() {
		return "Hello ! I am a ToDoList api :)";
	}

	public ResponseEntity<TodoListDto> addList(TodoListDto list, String username) throws Exception {
		log.debug("ToDo List /add Request: {}", list);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		TodoListDto todoList;
		try {

			if (list == null || username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Invalid_body;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_body);
				log.error("Invalid or missing body field. Request body can't be null..");
				throw new Exception("Invalid or missing body field. Request body can't be null..");
			}

			/* Check list name exists in db */
			User user = userService.getUserWithName(username);
			if (user == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found User: {} in the system", username);
				throw new Exception("Not found any User:" + username + " in the system");
			}

			if (!listService.isListExists(list.getName(), user.getId())) {
				list.setUser(user);
				todoList = listService.save(list);
			} else {
				errorCode = HttpStatus.CONFLICT;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Conflict_List_name);
				log.error("ToDoList 'name': {} already exists in system ", list.getName());
				throw new Exception("ToDoList 'name': " + list.getName()
						+ " already exists in system.Please send an unique name for creating new toDo list.. ");
			}

			log.info("ToDo list /add method response created.. Response: {}",
					objectMapper.writeValueAsString(todoList));
			log.info("ToDo list /add service transaction is finished succesfully..");

			return ResponseEntity.ok(todoList);
		} catch (Exception e) {
			log.error("ToDo List /add service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + ""); // app related code
			error.setReason(errorCode.name());// client tarafina iletilen aciklama
			error.setMessage(e.getMessage()); // hata detayi
			error.setStatus(errorCode.toString()); // http status code
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;

		} finally {
			log.debug("ToDo List /add Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	public ResponseEntity<String> deleteList(String listName, String username) throws Exception {
		log.debug("ToDo List /delete Request started. ListName: {}", listName);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		try {

			if (listName == null || username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"name\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"name\"  can't be null..");
			}
			User user = userService.getUserWithName(username);
			if (user == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found User: {} in the system", username);
				throw new Exception("Not found any User:" + username + " in the system");
			}

			boolean isDeleted = listService.deleteWithListName(listName, user.getId());

			if (!isDeleted) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any list in the system for deleting, With List Name:{} and UserName:{}", listName,
						username);
				throw new Exception("Not found any list in the system for deleting ,With List Name:  " + listName
						+ " and UserName:" + username);
			}

			return ResponseEntity.ok("List delete: " + listName);
		} catch (Exception e) {
			log.error("ToDo List /delete service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + ""); // app related code
			error.setReason(errorCode.name());// client tarafina iletilen aciklama
			error.setMessage(e.getMessage()); // hata detayi
			error.setStatus(errorCode.toString()); // http status code
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;
		} finally {
			log.debug("ToDo List /delete Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	public ResponseEntity<TodoListDto> getListName(String listName, String username) throws Exception {
		log.debug("ToDo List /getList Request started. ListName: {}", listName);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		TodoListDto list = new TodoListDto();
		try {

			if (listName == null || username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"name\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"name\"  can't be null..");
			}
			User user = userService.getUserWithName(username);
			if (user == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found User: {} in the system", username);
				throw new Exception("Not found any User:" + username + " in the system");
			}
			list.setUser(user);
			list = listService.getAllWithListName(listName, user.getId());

			if (list == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any list in the system with List Name:{} and UserName:{}", listName, username);
				throw new Exception(
						"Not found any list in the system with List Name:  " + listName + " and UserName:" + username);
			}

			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error("ToDo List /getList service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + ""); // app related code
			error.setReason(errorCode.name());// client tarafina iletilen aciklama
			error.setMessage(e.getMessage()); // hata detayi
			error.setStatus(errorCode.toString()); // http status code
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;
		} finally {

			log.debug("ToDo List /getList Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	public ResponseEntity<List<TodoListDto>> getAllListByUsername(String username) throws Exception {
		log.debug("ToDo List /getList Request started. UserName: {}", username);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		List<TodoListDto> list = new ArrayList<TodoListDto>();
		try {

			if (username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"name\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"name\"  can't be null..");
			}
			User user = userService.getUserWithName(username);

			if (user == null) {
				errorCode = HttpStatus.FORBIDDEN;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found any user with User Name:{}", username);
				throw new Exception("Not found any user with User Name:  " + username);
			}

			list = listService.getAllWithId(user.getId());

			if (list.size() <= 0) {
				errorCode = HttpStatus.FORBIDDEN;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any list in the system with User Name:{}", username);
				throw new Exception("Not found any list in the system with User Name:  " + username);
			}

			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error("ToDo List /getList service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + ""); // app related code
			error.setReason(errorCode.name());// client tarafina iletilen aciklama
			error.setMessage(e.getMessage()); // hata detayi
			error.setStatus(errorCode.toString()); // http status code
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;
		} finally {
			log.debug("ToDo List /getList Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

}

package com.todo.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.exception.ApiException;
import com.todo.exception.Error;
import com.todo.exception.ErrorSubCode;
import com.todo.exception.ErrorSubDetail;
import com.todo.model.dto.ListItemDto;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.HttpDetailStatus;
import com.todo.model.entity.ListItem;
import com.todo.model.entity.User;
import com.todo.service.ListItemService;
import com.todo.service.ListService;
import com.todo.service.UserService;

@RestController
public class ListItemApiController implements ListItemApi {
	private static final Logger log = LoggerFactory.getLogger(ListItemApiController.class);
	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;

	@Autowired
	private ListItemService listItemService;

	@Autowired
	private ListService listService;

	@Autowired
	private UserService userService;

	@Autowired
	public ListItemApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@Override
	public ResponseEntity<ListItemDto> addItem(ListItemDto listItem, String listName, String username)
			throws Exception {
		log.debug("List item /add Request: {}", listItem);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		ListItemDto listItemDto;
		try {

			if (listItem == null || username == null) {
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

			TodoListDto list = listService.getAllWithListName(listName, user.getId());
			if (list == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found List: {} in the system", listName);
				throw new Exception("Not found any List:" + listName + " in the system");
			}

			if (!listItemService.isListItemExists(listItem.getName(), list.getId(), user.getId())) {
				listItem.setList(list);
				listItemDto = listItemService.save(listItem);
			} else {
				errorCode = HttpStatus.CONFLICT;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Conflict_List_name);
				log.error("List item 'name': {} already exists in system ", listItem.getName());
				throw new Exception("List item 'name': " + listItem.getName()
						+ " already exists in system.Please send an unique name for creating new toDo list.. ");
			}

			log.info("List item service /add method response created.. Response: {}",
					objectMapper.writeValueAsString(listItemDto));
			log.info("List item service /add service transaction is finished succesfully..");

			return ResponseEntity.ok(listItemDto);
		} catch (Exception e) {
			log.error("List item service /add service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + "");
			error.setReason(errorCode.name());
			error.setMessage(e.getMessage());
			error.setStatus(errorCode.toString());
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;

		} finally {
			log.debug("List item service /add Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	@Override
	public ResponseEntity<String> deleteListItem(String listItemName, String listName, String username)
			throws Exception {
		log.debug("List item service/delete Request started. ListName: {}", listItemName);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		try {

			if (listItemName == null || username == null) {
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

			TodoListDto list = listService.getAllWithListName(listName, user.getId());
			if (list == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found List: {} in the system", listName);
				throw new Exception("Not found any List:" + listName + " in the system");
			}

			boolean isDeleted = listItemService.deleteWithListItemName(listItemName, list.getId(), user.getId());

			if (!isDeleted) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any list item in the system for deleting, With List Name:{} and UserName:{}",
						listItemName, username);
				throw new Exception("Not found any list item in the system for deleting ,With List Name:  "
						+ listItemName + " and UserName:" + username);
			}

			return ResponseEntity.ok("List item delete: " + listItemName);
		} catch (Exception e) {
			log.error("List item /delete service error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + "");
			error.setReason(errorCode.name());
			error.setMessage(e.getMessage());
			error.setStatus(errorCode.toString());
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;
		} finally {
			log.debug("List item /delete Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	@Override
	public ResponseEntity<ListItemDto> getListItem(String listItemName, String listName, String username)
			throws Exception {
		log.debug("List item service /getList Request started. UserName: {}", username);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		ListItemDto listItemDto = new ListItemDto();
		try {

			if (username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"username\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"username\"  can't be null..");
			}

			if (listItemName == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"listItemName\" can't be null..");
				throw new Exception(
						"Invalid or missing body field. Request  parameter: \"listItemName\"  can't be null..");
			}

			if (listName == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"listName\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"listName\"  can't be null..");
			}

			User user = userService.getUserWithName(username);

			if (user == null) {
				errorCode = HttpStatus.FORBIDDEN;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found any user with User Name:{}", username);
				throw new Exception("Not found any user with User Name:  " + username);
			}

			TodoListDto list = listService.getAllWithListName(listName, user.getId());
			if (list == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found List: {} in the system", listName);
				throw new Exception("Not found any List:" + listName + " in the system");
			}

			if (user.getId() != list.getUser().getId()) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found List item: {} in the system for User:{}", listItemName, username);
				throw new Exception("Not found any List item:" + listItemName + " in the system for User:" + username);
			}

			listItemDto = listItemService.getListItem(listItemName, list.getId());
 
			if (listItemDto == null) {
				errorCode = HttpStatus.FORBIDDEN;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any list item in the system with ListName: {} and User Name:{}", username);
				throw new Exception("Not found any list item in the system with  ListName: " + listName
						+ " and  User Name:  " + username);
			}

			return ResponseEntity.ok(listItemDto);
		} catch (Exception e) {
			log.error("List Item service /getListItem method error..." + e.getMessage(), e);
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

			log.debug(
					"List Item service /getListItem Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

}

package com.todo.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.todo.model.dto.UserDto;
import com.todo.model.entity.HttpDetailStatus;
import com.todo.model.entity.User;
import com.todo.service.UserService;

@RestController
public class UsersApiController implements UsersApi {
	private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<List<UserDto>> getUsers() throws Exception {
		log.debug("Users service /getUsers Request started.");

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		List<UserDto> list = new ArrayList<UserDto>();
		try {

			list = userService.getAll();

			if (list == null) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_user);
				log.error("Not found any User in the system");
				throw new Exception("Not found any User in the system");
			}

		} catch (Exception e) {
			log.error("User service /getUsers  error..." + e.getMessage(), e);
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
		}
		log.debug("User service /getUsers Request Process time:" + (System.currentTimeMillis() - startTime));
		return ResponseEntity.ok(list);
	}

	@Override
	public ResponseEntity<UserDto> addUser(@Valid UserDto userDto) throws Exception {
		log.debug("User service /add Request: {}", userDto);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		UserDto userDtoSaved;
		try {

			if (userDto == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Invalid_body;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_body);
				log.error("Invalid or missing body field. Request body can't be null..");
				throw new Exception("Invalid or missing body field. Request body can't be null..");
			}

			/* Check list name exists in db */
			if (!userService.isUserExists(userDto.getName())) {
				userDtoSaved = userService.save(userDto);
			} else {
				errorCode = HttpStatus.CONFLICT;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Conflict_List_name);
				log.error("User 'name': {} already exists in system ", userDto.getName());
				throw new Exception("User 'name': " + userDto.getName()
						+ " already exists in system.Please send an unique name for creating new User.. ");
			}

			log.info("User service /addUser method response created.. Response: {}",
					objectMapper.writeValueAsString(userDtoSaved));
			log.info("User service /addUser transaction is finished succesfully..");

			return ResponseEntity.ok(userDtoSaved);
		} catch (Exception e) {
			log.error("User service /add method error..." + e.getMessage(), e);
			if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR) {
				errorCodeDetail = HttpDetailStatus.Internal_error;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Internal_server_error);
			}

			/********* CREATE ERROR RESPONSE ***********/
			Error error = new Error();
			error.setCode(errorSubDetail.getErrorSubCode().getCode() + ""); // app related code
			error.setReason(errorCode.name());// client tarafina iletilen aciklama --> BAD_REQUEST
			error.setMessage(e.getMessage()); // hata detayi
			error.setStatus(errorCode.toString()); // http status code
			error.setHttpStatus(errorCode);
			error.setHttpDetailStatus(errorCodeDetail);
			ApiException apiException = new ApiException(error);

			throw apiException;

		} finally {
			log.debug("User service /add Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

	@Override
	public ResponseEntity<String> deleteUser(@Valid String username) throws Exception {
		log.debug("User service /delete Request started. Username: {}", username);

		long startTime = System.currentTimeMillis();
		ErrorSubDetail errorSubDetail = new ErrorSubDetail();
		HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpDetailStatus errorCodeDetail = null;
		try {

			if (username == null) {
				errorCode = HttpStatus.BAD_REQUEST;
				errorCodeDetail = HttpDetailStatus.Missing_request_param;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Invalid_or_missing_request_param);
				log.error("Invalid or missing body field. Request parameter: \"username\" can't be null..");
				throw new Exception("Invalid or missing body field. Request  parameter: \"username\"  can't be null..");
			}

			boolean isDeleted = userService.deleteWithUsername(username);

			if (!isDeleted) {
				errorCode = HttpStatus.NOT_FOUND;
				errorCodeDetail = HttpDetailStatus.Not_found_content;
				errorSubDetail.setErrorSubCode(ErrorSubCode.Not_found_data);
				log.error("Not found any USER in the system for deleting, With UserName:{}", username);
				throw new Exception("Not found any USER in the system for deleting ,With UserName:  " + username);
			}

			return ResponseEntity.ok("User :	" + username + "  deleted. ");
		} catch (Exception e) {
			log.error("ToDo List /delete service error..." + e.getMessage(), e);
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
			log.debug("ToDo List /delete Request Process time:" + (System.currentTimeMillis() - startTime));
		}
	}

}

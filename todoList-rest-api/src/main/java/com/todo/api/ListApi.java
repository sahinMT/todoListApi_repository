/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.todo.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.exception.Error;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.ListItem;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Validated
@RequestMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ListApi {

	@GetMapping("/")
	public String index();

	@Operation(description = "Add a list", tags = { "list" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "List created", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ListItem.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "List is already exists", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@PostMapping("/add")
	ResponseEntity<TodoListDto> addList(@RequestBody TodoListDto list,@RequestParam("username") String username) throws Exception;

	@Operation(summary = "Deletes a list", description = "This operation deletes a list", tags = { "list" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "List Item Deleted", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "??tem is already exists", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@DeleteMapping("/delete")
	ResponseEntity<String> deleteList(
			@ApiParam(value = "Identifier of the list", required = true) @RequestParam("name") String listName,@RequestParam("username") String username)
			throws Exception;

	@Operation(summary = "get all toDo list items with list name", description = "You can get for all list items with listname ", tags = {
			"list" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@GetMapping("/getList")
	ResponseEntity<TodoListDto> getListName(
			@ApiParam(value = "pass a list name for get all toDo list", required = true) @RequestParam("name") String name,@RequestParam("username") String username)
			throws Exception;

	@Operation(summary = "get all toDo list values for user", description = "You can get for all list for user", tags = {
			"list" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@GetMapping("/getUserList")
	ResponseEntity<List<TodoListDto>> getAllListByUsername(
			@ApiParam(value = "pass an user name for get all toDo list", required = true) @RequestParam("username") String username)
			throws Exception;

}

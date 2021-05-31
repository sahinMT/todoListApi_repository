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

import com.todo.model.dto.ListItemDto;
import com.todo.model.entity.ListItem;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Validated
@RequestMapping(path = "/listItem", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ListItemApi {

	@Operation(summary = "adds an item", description = "Adds an item to the list", tags = { "listItem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "item created", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ListItem.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "İtem is already exists", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@PostMapping("/addItem")
	ResponseEntity<ListItemDto> addItem(@RequestBody ListItemDto list, @RequestParam("listName") String listName,
			@RequestParam("username") String username) throws Exception;

	@Operation(summary = "Deletes a list item", description = "This operation deletes a list item", tags = {
			"listItem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "List Item Deleted", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ListItem.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "İtem is already exists", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@DeleteMapping("/deleteItem")
	ResponseEntity<String> deleteListItem(
			@ApiParam(value = "Identifier of the list item", required = true) @RequestParam("itemName") String listItemName,
			@RequestParam("listName") String listName, @RequestParam("username") String username) throws Exception;

	@Operation(summary = "get all toDo list items for list and user", description = "You can get for all list items for list", tags = {
			"listItem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ListItem.class)))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Error.class)))) })
	@GetMapping("/getItem")
	ResponseEntity<ListItemDto> getListItem(
			@ApiParam(value = "pass a list name for get all toDo list item", required = true) @RequestParam("itemName") String listItemName,
			@RequestParam("listName") String listName, @RequestParam("username") String username) throws Exception;

}

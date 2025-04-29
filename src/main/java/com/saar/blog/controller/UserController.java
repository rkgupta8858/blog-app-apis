package com.saar.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saar.blog.payloads.ApiResponse;
import com.saar.blog.payloads.UserDto;
import com.saar.blog.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto=  userService.addUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId) {
		UserDto update=  userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(update,HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
	UserDto userDto=userService.getUserById(userId);
	return  ResponseEntity.ok(userDto);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity< List<UserDto>> getAllUser() 
	{
		List<UserDto>list= userService.getAllUser();
		return new ResponseEntity<List<UserDto>>(list,HttpStatus.CREATED);
	}
	
	// Only ADMIN role vala insan hi user delete kr sakta hai
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable  Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User is deleted !!!", true),HttpStatus.OK);
	}
}

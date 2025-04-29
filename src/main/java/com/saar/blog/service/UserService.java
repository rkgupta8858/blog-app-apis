package com.saar.blog.service;

import java.util.List;

import com.saar.blog.payloads.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto userDto);
	 UserDto addUser(UserDto userDto);
	 UserDto updateUser(UserDto userDto , Integer userId);
	 UserDto getUserById(Integer userId);
	 List<UserDto>getAllUser();
	 void deleteUser(Integer userId);
}

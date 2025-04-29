package com.saar.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saar.blog.config.AppConstants;
import com.saar.blog.entity.Role;
import com.saar.blog.entity.User;
import com.saar.blog.exception.ResourceNotFoundException;
import com.saar.blog.payloads.UserDto;
import com.saar.blog.repositories.RoleRepo;
import com.saar.blog.repositories.UserRepo;
import com.saar.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
   private	UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper; // This is use for converting User to UserDto and vice versa

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto addUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User createUser=userRepo.save(user);
		return this.userToDto(createUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		 User user = this.userRepo.findById(userId)
			        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		 user.setName(userDto.getName());
		 user.setPassword(userDto.getPassword());
		 user.setEmail(userDto.getEmail());
		 user.setAbout(userDto.getAbout());
		 User updatedUser=userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		 User user = this.userRepo.findById(userId)
			        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUser=userRepo.findAll();
		List<UserDto> userDtos=allUser.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}
	/**
	 * allUser.stream():

	यह allUser (जो कि एक List<User> है) को Stream में बदल देता है।
	Stream डेटा को प्रोसेस करने के लिए एक पाइपलाइन प्रदान करता है, जिसमें आप डेटा को फ़िल्टर, मैप, और कलेक्ट कर सकते हैं।
	map(user -> this.userToDto(user)):

		यह Stream के हर user को प्रोसेस करता है।
	userToDto(user) एक मेथड है, जो User ऑब्जेक्ट को UserDto में बदलता है।
	यहां user -> this.userToDto(user) एक lambda expression है, जो हर user पर यह मेथड लागू करता है।
	collect(Collectors.toList()):

	प्रोसेस किए गए Stream को वापस List में बदल देता है।
	इसका मतलब है कि प्रोसेसिंग के बाद जो भी UserDto ऑब्जेक्ट्स तैयार हुए हैं, उन्हें एक नई List (userDtos) में इकट्ठा कर लिया गया।*/

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);

	}
	
	
	// DTO TO User Changing
	
	User dtoToUser(UserDto userDto)
	{
		// This is normal way to convert data  UserDto To User
//		User user= new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		User user=modelMapper.map(userDto, User.class);
		return user;
	}
	
	UserDto userToDto(User user)
	{
		// Change this User data into Dto
//		UserDto ud=new UserDto();
//		ud.setId(user.getId());
//		ud.setName(user.getName());
//		ud.setEmail(user.getEmail());
//		ud.setPassword(user.getPassword());
//		ud.setAbout(user.getAbout());
		
		UserDto ud=modelMapper.map(user, UserDto.class);
		return ud;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// roles
		Role role= this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		User newUser=this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}

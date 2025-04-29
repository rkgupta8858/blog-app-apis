package com.saar.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
//	@NotNull
	@NotEmpty // this annotation is combination of @NotNull and also check of empty() means @NotEmpty
	@Size(min=3, message="Username must be min of 4 character")
	private String name;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10, message="Username must be min of 4 character and max 10 character")
	private String password;
	
	@Size(min=10, message="Write atleast 10 letter in about ")
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();

}

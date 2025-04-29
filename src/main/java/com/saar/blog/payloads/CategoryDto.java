package com.saar.blog.payloads;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min=5,message = "Write category of post minimum 5 letter")
	private String categoryTitle;
	@NotEmpty
	@Size(min=12,message="write something about post description")
	private String categoryDescription;
}

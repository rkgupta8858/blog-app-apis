package com.saar.blog.service;

import com.saar.blog.payloads.CategoryDto;
import java.util.List;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer cId);
	void deleteCategory(Integer cId);
	CategoryDto getCategory(Integer cId);
	List <CategoryDto>getAllCategory();
}

package com.saar.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.blog.entity.Category;
import com.saar.blog.exception.ResourceNotFoundException;
import com.saar.blog.payloads.CategoryDto;
import com.saar.blog.repositories.CategoryRepo;
import com.saar.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto,Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	public List<CategoryDto> getAllCategory() {
	    List<Category> allCategory = categoryRepo.findAll();
	    List<CategoryDto> allCategoryDto = allCategory.stream()
	            .map(cat -> modelMapper.map(cat, CategoryDto.class))
	            .collect(Collectors.toList());
	    return allCategoryDto;
	
		/*
		 Stream बनाना (allUser.stream()):

		allUser एक Collection (जैसे List या Set) है, जिसमें User Objects हैं।
		.stream() इस Collection को एक Stream में बदल देता है।
		Stream डेटा प्रोसेसिंग के लिए Functional तरीके से काम करने की सुविधा देता है।
		Transformation (map(...)):

		.map(user -> this.userToDto(user)):
		यह हर user Object को प्रोसेस करता है और उसे UserDto में बदल देता है।
		यह Transformation this.userToDto(user) Method के जरिए हो रहा है।
		Stream को List में बदलना (collect(Collectors.toList())):

		.collect() Method का उपयोग Stream के प्रोसेस किए गए डेटा को इकठ्ठा करने के लिए किया जाता है।
		Collectors.toList() एक Collector है, जो Stream के डेटा को List के रूप में बदल देता है।
		प्रोसेसिंग के बाद हर UserDto को नई List userDtos में जोड़ दिया जाता है।
		 */
	}
	
	CategoryDto categoryToDto(Category category)
	{
		return modelMapper.map(category, CategoryDto.class);
	}
	
	Category dtoToCategory(CategoryDto categoryDto)
	{
		return modelMapper.map(categoryDto, Category.class);
	}
}

package com.saar.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saar.blog.payloads.CategoryDto;
import com.saar.blog.service.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController 
{	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
	CategoryDto  categoryDto2=categoryService.addCategory(categoryDto);
	return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
		CategoryDto categoryDto3=categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(categoryDto3,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		String msg="Deleted Employee No is :"+categoryId;
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto categoryDto4=categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto4,HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> list=categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(list,HttpStatus.OK);
	}
}

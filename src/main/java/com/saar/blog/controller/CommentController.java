package com.saar.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saar.blog.payloads.ApiResponse;
import com.saar.blog.payloads.CommentDto;
import com.saar.blog.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
		CommentDto cd=this.commentService.addComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(cd,HttpStatus.CREATED);
		
	}
	@PostMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer id)
	{
		this.commentService.deleteComment(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Delete comment No is :"+id,true) ,HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	

}

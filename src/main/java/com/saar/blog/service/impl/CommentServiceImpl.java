package com.saar.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.blog.entity.Comment;
import com.saar.blog.entity.Post;
import com.saar.blog.exception.ResourceNotFoundException;
import com.saar.blog.payloads.CommentDto;
import com.saar.blog.repositories.CommentRepo;
import com.saar.blog.repositories.PostRepo;
import com.saar.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto addComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment"," id", commentId));
		this.commentRepo.delete(com);
	}
	
	
	
	
	
	
	
	
	

}

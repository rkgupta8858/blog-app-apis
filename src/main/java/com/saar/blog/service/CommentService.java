package com.saar.blog.service;

import com.saar.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto addComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
	

}

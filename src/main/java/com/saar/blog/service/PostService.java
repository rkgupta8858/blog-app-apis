package com.saar.blog.service;

import java.util.List;

import com.saar.blog.payloads.PostDto;
import com.saar.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);
	PostDto getPostById(Integer postId);
	List<PostDto>getPostByCategory(Integer categoryId);//get all posts by category
	List<PostDto>getPostsByUser(Integer userId);//get all posts by user
	// search posts
	List<PostDto> searchPosts(String keywords);

}

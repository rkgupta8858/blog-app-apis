package com.saar.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.blog.entity.Category;
import com.saar.blog.entity.Post;
import com.saar.blog.entity.User;
import com.saar.blog.exception.ResourceNotFoundException;
import com.saar.blog.payloads.PostDto;
import com.saar.blog.payloads.PostResponse;
import com.saar.blog.repositories.CategoryRepo;
import com.saar.blog.repositories.PostRepo;
import com.saar.blog.repositories.UserRepo;
import com.saar.blog.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
	Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
	User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
	Post post= this.modelMapper.map(postDto,Post.class);
	post.setImgName("default.png");
	post.setAddeddate(new Date());
	post.setCategory(category);
	post.setUser(user);
	
	Post newPost=this.postRepo.save(post);
	PostDto postDto2=this.modelMapper.map(newPost, PostDto.class);
	return postDto2;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		post.setTitle(postDto.getTitle());
		post.setImgName(postDto.getImgName());
		post.setContent(postDto.getContent());
		Post updatePost=this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		postRepo.delete(post);

	}
//	@Override
//	public List<PostDto> getAllPost() {
//		List<Post> posts=postRepo.findAll();
//		List<PostDto>postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		return postDtos;
//	}
	
	// this is code with pagination
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc") )
		{
			sort=sort.by(sortBy).ascending();
		}
		else {
			sort=sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> posts=pagePost.getContent();
		List<PostDto>postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts=postRepo.findByCategory(category);
		List<PostDto>postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
	List<Post> posts=postRepo.findAllByUser(user);
	List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)) 
		            .collect(Collectors.toList());
		return postDtos; 
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		List<Post> posts=this.postRepo.findByTitleContaining(keywords);
		List<PostDto>allPosts=posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPosts;
	}

}

package com.saar.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saar.blog.config.AppConstants;
import com.saar.blog.payloads.PostDto;
import com.saar.blog.payloads.PostResponse;
import com.saar.blog.service.FileService;
import com.saar.blog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/add/id/{id}/categoryId/{categoryId}")
	ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @PathVariable Integer id,@PathVariable Integer categoryId)
	{
		PostDto postDto1= postService.createPost(postDto, id, categoryId);
		return new ResponseEntity<PostDto>(postDto1,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer id)
	{
		PostDto postDto1= postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(postDto1,HttpStatus.CREATED);
	}
	
	
	// Get All posts of this category
	@GetMapping("/categoryId/{categoryId}")
	ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postDtos= postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/userId/{id}")
	ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer id)
	{
		List<PostDto> postDtos= postService.getPostsByUser(id);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	@DeleteMapping ("/delete/{postId}")
	ResponseEntity<String> deletePost(@PathVariable Integer postId)
	{
		postService.deletePost(postId);
		return new ResponseEntity<String>("You Deleted postId is :"+postId,HttpStatus.OK);
	}
	
	
	@GetMapping("/get/{postId}")
	ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto postDto=postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	
//	@GetMapping("/getAll")
//	ResponseEntity<List<PostDto>> getAllPost()
//	{
//		List<PostDto> postDtos= postService.getAllPost();
//		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
//	}
	
	
	//To access data in sorting =  http://localhost:8080/post/getAll?pageNumber=0&pageSize=5&sortBy=postId&sortDir=desc
	@GetMapping("/getAll")
	ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required=false) String sortDir
			)
	{
		PostResponse postDtos= postService.getAllPost(pageNumber, pageSize,sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/getPostBy/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords) {
		List<PostDto> allPosts= postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(allPosts,HttpStatus.OK);
	}
	
	//post images upload
	@PostMapping("/image/upload/{postId}")
	 public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException
	 
	 {
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImgName(fileName);
		PostDto updatePost= this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	 }
	
	// method to Get an image in browser through url
	
	@GetMapping(value="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE) 
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}











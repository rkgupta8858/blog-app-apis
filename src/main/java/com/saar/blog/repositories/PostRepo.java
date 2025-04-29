package com.saar.blog.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saar.blog.entity.Category;
import com.saar.blog.entity.Post;
import com.saar.blog.entity.User;


@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findAllByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title); // Title hamare field ka naam hai

}

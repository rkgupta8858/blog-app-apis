package com.saar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saar.blog.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
}

package com.saar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saar.blog.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

package com.saar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saar.blog.entity.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}

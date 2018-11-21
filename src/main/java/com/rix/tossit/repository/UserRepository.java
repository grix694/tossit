package com.rix.tossit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rix.tossit.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserId(Long userId);
	
	User findByUserName(String userName);
	
	
}

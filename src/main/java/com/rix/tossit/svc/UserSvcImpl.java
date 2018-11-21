package com.rix.tossit.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rix.tossit.entity.User;
import com.rix.tossit.repository.UserRepository;

@Service
public class UserSvcImpl implements UserSvc {
	
	@Autowired
	UserRepository userRepository;
	
	public UserSvcImpl() {
		
	}

	@Override
	public User getUser(long userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User create(User user) {
		return userRepository.save(user);
	}

}

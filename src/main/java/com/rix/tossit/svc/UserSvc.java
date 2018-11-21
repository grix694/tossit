package com.rix.tossit.svc;

import com.rix.tossit.entity.User;

public interface UserSvc {
	
	User create(User user);
	
	User getUser(long userId);
	
	User getUser(String userName);
}

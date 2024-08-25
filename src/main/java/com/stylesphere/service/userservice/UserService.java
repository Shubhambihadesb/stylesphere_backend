package com.stylesphere.service.userservice;

import com.stylesphere.exception.UserException;
import com.stylesphere.model.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
}

package com.stylesphere.service.userservice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stylesphere.config.JwtProvider;
import com.stylesphere.exception.UserException;
import com.stylesphere.model.User;
import com.stylesphere.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImpl(UserRepository userRepository,JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		
		Optional<User>user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("user not found with id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not found with email : "+email);
			
		}
		
		return user;
	}

}

package com.stylesphere.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stylesphere.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
}

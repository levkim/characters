package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	public boolean existsUsername(String username);
	public User findByUsername(String username);
	public User findByEmail(String email);

}

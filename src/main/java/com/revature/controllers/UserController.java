package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
	
	private UserDAO dao;
	
	@Autowired
	public UserController(UserDAO dao) {
		super();
		this.dao = dao;
	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(dao.findAll());
	}
	
	@GetMapping
	public ResponseEntity<User> getUser(String name) {
		User u = dao.findByUsername(name);
		if (u == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(u);
		}
	}
	
	@PostMapping
	public ResponseEntity<User> add(@RequestBody User u) {
		int id = u.getId();
		
		if (id != 0) {
			return ResponseEntity.badRequest().build();
		}
		
		dao.save(u);
		return ResponseEntity.status(201).body(u);
	}
	
	@PatchMapping
	public ResponseEntity<User> update(int id) {
		return null;
	}
	
	@DeleteMapping
	public ResponseEntity<User> delete(int id) {
		Optional<User> option = dao.findById(id);
		
		if (option.isPresent()) {
			dao.delete(option.get());
			return ResponseEntity.accepted().body(option.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
}

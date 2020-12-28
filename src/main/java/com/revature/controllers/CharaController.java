package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Chara;
import com.revature.models.User;
import com.revature.repositories.CharaDAO;
import com.revature.repositories.UserDAO;

@RestController
@RequestMapping("character")
@CrossOrigin
public class CharaController {
	
	private UserDAO udao;
	private CharaDAO cdao;
	
	@Autowired
	public CharaController(CharaDAO cdao, UserDAO udao) {
		super();
		this.udao = udao;
		this.cdao = cdao;
	}
	
	@GetMapping
	public ResponseEntity<List<Chara>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(cdao.findAll());
	}
	
	@GetMapping(value="/creator/{name}")
	public ResponseEntity<List<Chara>> getAllByUser(@PathVariable("username") String username) {
		User u = udao.findByUsername(username);
		if (u == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(cdao.findByUser(u));
		}
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Chara> getChara(@PathVariable("id") int id) {
		Chara c = cdao.getOne(id);
		if (c == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(c);
		}
	}
	
	@PostMapping
	public ResponseEntity<Chara> add(@RequestBody Chara c) {
		int id = c.getCharId();
		
		if (id != 0) {
			return ResponseEntity.badRequest().build();
		}
		
		cdao.save(c);
		return ResponseEntity.status(201).body(c);
	}
	
	@DeleteMapping
	public ResponseEntity<Chara> delete(@PathVariable("id") int id) {
		Optional<Chara> option = cdao.findById(id);
		
		if (option.isPresent()) {
			cdao.delete(option.get());
			return ResponseEntity.accepted().body(option.get());
		}
		
		return ResponseEntity.notFound().build();
	}

}

package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;
import com.revature.models.Chara;

@Repository
public interface CharaDAO extends JpaRepository<Chara, Integer> {
	
	public List<Chara> findByUserId(int creatorId);
	public List<Chara> findByUser(User u);

}

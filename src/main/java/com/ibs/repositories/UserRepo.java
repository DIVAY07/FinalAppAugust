package com.ibs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibs.entities.Account;
import com.ibs.entities.User1;

public interface UserRepo extends JpaRepository<User1, Integer>{
	public Optional<User1> findByaccNo(Integer id);

}

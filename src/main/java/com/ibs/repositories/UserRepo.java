package com.ibs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibs.entities.Account;
import com.ibs.entities.User1;
import com.ibs.payloads.User1Dto;

public interface UserRepo extends JpaRepository<User1, Integer>{
	public Optional<User1> findByaccNo(Integer id);
//	public  existsById(Integer id);
//	public List<User1>findByIsApproved(Integer var);
//	public Optional<List<User1>> findByIsApproved(Boolean var);
//	public User1 findByaccNo(Integer id);
//}
}
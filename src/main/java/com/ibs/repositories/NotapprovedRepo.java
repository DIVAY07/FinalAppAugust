package com.ibs.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibs.entities.Notapproved;
import com.ibs.entities.User1;

public interface NotapprovedRepo extends JpaRepository<Notapproved, Integer>{
	

}

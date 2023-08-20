package com.ibs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ibs.entities.User1;

public interface UserRepo extends JpaRepository<User1, Integer>{

}

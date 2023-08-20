package com.ibs.services;

import java.util.List;

import com.ibs.payloads.User1Dto;


public interface UserService {
	User1Dto createUser(User1Dto user);
	User1Dto updateUser(User1Dto userDto , Integer userId);
	User1Dto getUserById(Integer userId);
	List<User1Dto> getAllUsers();
	void deleteUser(Integer userId);
}

package com.ibs.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.entities.User1;
import com.ibs.payloads.ApiResponse;
import com.ibs.payloads.User1Dto;
import com.ibs.services.impl.*;

import jakarta.validation.Valid;

//import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired 
	private AccountServiceImpl accservice; 
	
	@PostMapping("/openaccount")
	public ResponseEntity<User1Dto> createUser(@Valid @RequestBody User1Dto userDto)
	{
//		System.out.println("the openaccount api is working till here");
		User1Dto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<User1Dto> updateUser(@Valid @RequestBody User1Dto userDto , @PathVariable("userId") Integer uid)
	{
		User1Dto updatedUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}
	
//	@DeleteMapping("/{userId}")
//	
//	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid)
//	{
//		this.userService.deleteUser(uid);
//		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully" , true),HttpStatus.OK);
//	}
	
	
	
	@GetMapping("/admin/userlist_approved")
	public ResponseEntity<List<User1Dto>> getAllUsersthatareApp()
	{
		return ResponseEntity.ok(this.userService.getAllUsersApproved(true));
	}
	
	@GetMapping("admin/userlist_requested")
	public ResponseEntity<List<User1Dto>> getAllUsersthatareReq()
	{
		return ResponseEntity.ok(this.userService.getAllUsersRequested(false));
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<User1> getSingleUser(@PathVariable("userId") Integer uid)
	{
		return ResponseEntity.ok(this.userService.getUserById(uid));
	}
	
}

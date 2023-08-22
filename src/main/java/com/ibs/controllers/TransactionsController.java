package com.ibs.controllers;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.payloads.ApiResponse;
import com.ibs.payloads.*;
import com.ibs.payloads.User1Dto;
import com.ibs.security.JwtHelper;
import com.ibs.payloads.AccountDto;
import com.ibs.services.impl.*;
import com.ibs.entities.JwtRequest;
import com.ibs.entities.JwtResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")


public class TransactionsController {

	@Autowired
	private TransServiceImpl transService;
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private AccountServiceImpl accService;
	
	  @GetMapping("/userDashboard/{userId}")
	    public ResponseEntity<User1Dto> getSingleUser(@PathVariable("userId") String userId)
		{
			AccountDto acc = accService.getUserById(userId);
	    	return ResponseEntity.ok(this.userService.getUserById(acc.getAccNo()));
		}
	  @PostMapping("/userDashboard/{userId}/fundTransfer")
	  public ResponseEntity<TransactionsDto> CreateTrans(@Valid @RequestBody TransactionsDto trans)
	  {	
		  User1Dto current = this.userService.getUserById(trans.getPayee());
		  User1Dto current1 = this.userService.getUserById(trans.getPayee());
		  current.setAccBalance( current.getAccBalance() - trans.getAmount());		  
		  current1.setAccBalance( current1.getAccBalance() + trans.getAmount());
		  TransactionsDto createdTrans = this.transService.createTrans(trans);
		  return new ResponseEntity<>(createdTrans, HttpStatus.CREATED);
	  }
	  
	  @GetMapping("/userDashboard/{userId}/Transactions")
	 
		public ResponseEntity<List<TransactionsDto>> getTransById(@PathVariable("userId") String userId)
		{
		    AccountDto acc = accService.getUserById(userId);
		    List<TransactionsDto> merged = this.transService.getTransByPayee(acc.getAccNo());
		    merged.addAll(this.transService.getTransByReceiver(acc.getAccNo()));
		    
			return ResponseEntity.ok(merged);
			
		}
	   
}

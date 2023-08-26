package com.ibs.controllers;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
import com.ibs.repositories.UserRepo;
import com.ibs.security.JwtHelper;
import com.ibs.payloads.AccountDto;
import com.ibs.services.impl.*;
import com.ibs.entities.JwtRequest;
import com.ibs.entities.JwtResponse;
import com.ibs.entities.User1;
import com.ibs.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")


public class TransactionsController {

	@Autowired
	private TransServiceImpl transService;
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserRepo userrepo;
	
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AccountServiceImpl accService;
	
	  @GetMapping("/userDashboard/{userId}")
	    public ResponseEntity<User1> getSingleUser(@PathVariable("userId") String userId)
		{
			AccountDto acc = accService.getUserById(userId);
	    	return ResponseEntity.ok(this.userService.getUserById(acc.getAccNo()));
		}
	  @PostMapping("/userDashboard/fundTransfer/{userId}")
	  public ResponseEntity<TransactionsDto> CreateTrans(@Valid @RequestBody TransactionsDto trans)
	  {	
		  User1 current = this.userService.getUserById(trans.getPayer());
//		  User1 current1 = this.userService.getUserById(trans.getReceiver());
		  Optional<User1> current1 = this.userrepo.findByaccNo(trans.getReceiver());
			if(current1.isEmpty()== true)
			{
				System.out.println("receiver not there");
				throw new ResourceNotFoundException("Receiver Not Found", "Pls Check", trans.getReceiver());
			}
		  
			else  if(current.getAccNo()== trans.getReceiver())
		  {
			  System.out.println("Same payer and receiver");
			  throw new ResourceNotFoundException("Account number", "same , can't pay", current.getAccNo());
		  }
		  
		  else if(current.getAccBalance()<trans.getAmount())
		  {
			  System.out.println("Insufficient Balance");
			  throw new ResourceNotFoundException("Insufficient Balance", "Pls Add Money", current.getAccNo());
		  }
		  else {
			  User1 current12 = this.userService.getUserById(trans.getReceiver());
		  current.setAccBalance(current.getAccBalance() - trans.getAmount());
		  current12.setAccBalance( current12.getAccBalance()+ trans.getAmount());
		  
		  TransactionsDto createdTrans = this.transService.createTrans(trans);

	 
		  return new ResponseEntity<>(createdTrans, HttpStatus.CREATED);
		  }
	  }
	  @PostMapping("/userDashboard/withdrawl/{userId}")
	  public ResponseEntity<Integer> withdrawamount(@Valid @RequestBody Integer amount ,@PathVariable("userId") String userId)
	  
	  {
		 AccountDto acc = this.accService.getUserById(userId);

		 User1 user = this.userService.getUserById(acc.getAccNo());
		 user.setAccBalance(user.getAccBalance() - amount);
		 return new ResponseEntity<>(user.getAccBalance(),HttpStatus.CREATED);
	  }
	  
	  @GetMapping("/userDashboard/showTransactions/{userId}")
	 
		public ResponseEntity<List<TransactionsDto>> getTransById(@PathVariable("userId") String userId)
		{
		    AccountDto acc = accService.getUserById(userId);
		    List<TransactionsDto> merged = this.transService.getTransByPayer(acc.getAccNo());
		    merged.addAll(this.transService.getTransByReceiver(acc.getAccNo()));
		    
			return ResponseEntity.ok(merged);
			
		}
	  
//	  
//	  private User1 dtoToUser(User1Dto userDto)
//		{
//			User1 user = this.modelMapper.map(userDto, User1.class);
//			return user;
//		}
//		
//		public User1Dto userToDto(User1 user)
//		{
//			User1Dto userDto = this.modelMapper.map(user, User1Dto.class);
//			return userDto;
//	    }
//	   
}

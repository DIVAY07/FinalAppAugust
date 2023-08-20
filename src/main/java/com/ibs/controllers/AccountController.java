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
import com.ibs.payloads.User1Dto;
import com.ibs.security.JwtHelper;
import com.ibs.payloads.AccountDto;
import com.ibs.services.impl.*;
import com.ibs.entities.JwtRequest;
import com.ibs.entities.JwtResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")

public class AccountController{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtHelper helper;
	
	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	
	
	@Autowired
	private AccountServiceImpl accountService;
	
	 @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
	        this.doAuthenticate(request.getUserId(), request.getLoginPass());
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserId());
	        System.out.println(userDetails.getUsername());
	        System.out.println(userDetails.getPassword());
	        String token = this.helper.generateToken(userDetails);

	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .userId(userDetails.getUsername()).build();
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    private void doAuthenticate(String userId, String loginPass) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, loginPass);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	            throw new BadCredentialsException(" Invalid Username or Password  !!");
	        }

	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public String exceptionHandler() {
	        return "Credentials Invalid !!";
	    }
	@GetMapping("/test")
	public String testing()
	{
		return "Welcome you are in the testing phase";
	}
	@PostMapping("/register")
	public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto)
	{
		AccountDto createAccountDto = this.accountService.createAccount(accountDto);
		return new ResponseEntity<>(createAccountDto, HttpStatus.CREATED);
	}
	
}
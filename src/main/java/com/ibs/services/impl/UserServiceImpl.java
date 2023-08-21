package com.ibs.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.entities.Account;
import com.ibs.entities.User1;
import com.ibs.exceptions.ResourceNotFoundException;
import com.ibs.payloads.User1Dto;
import com.ibs.repositories.AccountRepo;
import com.ibs.repositories.UserRepo;
import com.ibs.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired private AccountRepo accRepo;
	
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public User1Dto createUser(User1Dto userDto) {
		// TODO Auto-generated method stub
		User1 user = this.dtoToUser(userDto);
		User1 savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public User1Dto updateUser(User1Dto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User1 user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User" , "id" , userId));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setMobile(userDto.getMobile());
		user.setEmail(userDto.getEmail());
		user.setAadharNo(userDto.getAadharNo());
		user.setPanNo(userDto.getPanNo());
		user.setDob(null);
		
		
		User1 updatedUser = this.userRepo.save(user);
		User1Dto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public User1Dto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User1 user = this.userRepo.findByaccNo(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<User1Dto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User1> users = this.userRepo.findAll();
		List<User1Dto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User1 user =  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	private User1 dtoToUser(User1Dto userDto)
	{
		User1 user = this.modelMapper.map(userDto, User1.class);
		return user;
	}
	
	public User1Dto userToDto(User1 user)
	{
		User1Dto userDto = this.modelMapper.map(user, User1Dto.class);
		return userDto;
    }

}

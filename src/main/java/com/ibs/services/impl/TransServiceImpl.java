package com.ibs.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.entities.Transactions;
import com.ibs.entities.User1;
import com.ibs.payloads.TransactionsDto;
import com.ibs.payloads.User1Dto;
import com.ibs.repositories.TransRepo;
import com.ibs.repositories.UserRepo;
import com.ibs.services.TransactionsService;
import com.ibs.services.impl.*;

@Service
public class TransServiceImpl implements TransactionsService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TransRepo transrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	
	@Override
	public TransactionsDto createTrans(TransactionsDto transdto) 
	{
		
		Transactions trans = dtoToTrans(transdto);
		
//		 User1 current = this.userrepo.findByaccNo(trans.getPayee());
//		  User1 current1 = this.userrepo.getById(trans.getPayee());
//		  current.setAccBalance( current.getAccBalance() - trans.getAmount());		  
//		  current1.setAccBalance( current1.getAccBalance() + trans.getAmount());
		 
		Transactions savedtrans = this.transrepo.save(trans);
		return transToDto(savedtrans);
	}
	
	
	public List<TransactionsDto> getTransByPayer(Integer payee) {
		// TODO Auto-generated method stub
		List<Transactions> trans = this.transrepo.findByPayer(payee);
		List<TransactionsDto> transDtos=trans.stream().map(tran->this.transToDto(tran)).collect(Collectors.toList());
		return transDtos;
	}

	public List<TransactionsDto> getTransByReceiver(Integer payee) {
		// TODO Auto-generated method stub
		List<Transactions> trans = this.transrepo.findByReceiver(payee);
		List<TransactionsDto> transDtos=trans.stream().map(tran->this.transToDto(tran)).collect(Collectors.toList());
		return transDtos;
	}
	
	
	
	
	
	private Transactions dtoToTrans(TransactionsDto transdto)
	{
		Transactions trans = this.modelMapper.map(transdto, Transactions.class);
		return trans;
	}
	
	public TransactionsDto transToDto(Transactions trans)
	{
		TransactionsDto transDto = this.modelMapper.map(trans, TransactionsDto.class);
		return transDto;
    }

}

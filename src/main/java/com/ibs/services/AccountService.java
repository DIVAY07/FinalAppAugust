package com.ibs.services;

import java.util.List;

import com.ibs.payloads.AccountDto;

public interface AccountService {
	AccountDto createAccount(AccountDto account);
	AccountDto getUserById(String userId);
	
}

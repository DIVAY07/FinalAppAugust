package com.ibs.services;import java.util.List;

import com.ibs.payloads.TransactionsDto;
public interface TransactionsService {
	TransactionsDto createTrans(TransactionsDto trans);
	List<TransactionsDto> getTransByPayer(Integer payee);
	List<TransactionsDto> getTransByReceiver(Integer payee);
}

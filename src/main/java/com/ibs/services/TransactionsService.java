package com.ibs.services;import java.util.List;

import com.ibs.payloads.TransactionsDto;
public interface TransactionsService {
	TransactionsDto createTrans(TransactionsDto trans);
	List<TransactionsDto> getTransByPayee(Integer payee);
	List<TransactionsDto> getTransByReceiver(Integer payee);
}

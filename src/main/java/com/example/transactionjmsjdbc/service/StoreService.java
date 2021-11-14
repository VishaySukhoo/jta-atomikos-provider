package com.example.transactionjmsjdbc.service;


import com.example.transactionjmsjdbc.domain.customer.Customer;
import com.example.transactionjmsjdbc.domain.order.Order;
import com.example.transactionjmsjdbc.exception.NoRollbackException;
import com.example.transactionjmsjdbc.exception.StoreException;

public interface StoreService {
	
	void store(Customer customer, Order order) throws Exception;
	
	void storeWithStoreException(Customer customer, Order order) throws StoreException;
	
	void storeWithNoRollbackException(Customer customer, Order order) throws NoRollbackException;

}

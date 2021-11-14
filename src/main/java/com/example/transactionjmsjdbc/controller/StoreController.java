package com.example.transactionjmsjdbc.controller;


import com.example.transactionjmsjdbc.domain.customer.Customer;
import com.example.transactionjmsjdbc.domain.order.Order;
import com.example.transactionjmsjdbc.exception.NoRollbackException;
import com.example.transactionjmsjdbc.exception.StoreException;
import com.example.transactionjmsjdbc.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

	@Autowired
	private StoreService storeService;

	@RequestMapping("/store")
	public ResponseEntity<Void> store() throws Exception {
		Customer customer = new Customer();
		customer.setName("Vishay");
		customer.setAge(25);
		Order order = new Order();
		order.setQuantity(3);
		order.setCode(18);
		storeService.store(customer, order);
		return ResponseEntity.ok().build();
	}

	@RequestMapping("/storeException")
	public ResponseEntity<Void> storeWithStoreException() throws StoreException {
		Customer customer = new Customer();
		customer.setName("Vishay");
		customer.setAge(25);
		Order order = new Order();
		order.setQuantity(3);
		order.setCode(18);
		storeService.storeWithStoreException(customer, order);
		return ResponseEntity.ok().build();
	}

	@RequestMapping("/storeWithNoRollbackException")
	public ResponseEntity<Void> storeWithNoRollbackException() throws NoRollbackException {
		Customer customer = new Customer();
		customer.setName("Vishay");
		customer.setAge(25);
		Order order = new Order();
		order.setQuantity(3);
		order.setCode(18);
		storeService.storeWithNoRollbackException(customer, order);
		return ResponseEntity.ok().build();
	}
}

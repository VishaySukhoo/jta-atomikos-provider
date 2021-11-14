package com.example.transactionjmsjdbc.service;

import com.example.transactionjmsjdbc.domain.customer.Customer;
import com.example.transactionjmsjdbc.domain.order.Order;
import com.example.transactionjmsjdbc.exception.NoRollbackException;
import com.example.transactionjmsjdbc.exception.StoreException;
import com.example.transactionjmsjdbc.repository.customer.CustomerRepository;
import com.example.transactionjmsjdbc.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.transactionjmsjdbc.jms.Configurations.MESSAGE_QUEUE;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
	@Autowired
	private JmsTemplate jmsTemplate;


	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	@Transactional
	public void store(Customer customer, Order order) {
		customerRepository.save(customer);
		orderRepository.save(order);
		jmsTemplate.convertAndSend(MESSAGE_QUEUE,  "JMS received message from /store : " );
	}

//	this method rolls back for StoreException error, which is what we throw. so no entities will be written and nothing will
//	be placed on the queue
	@Transactional(rollbackFor = StoreException.class)
	@Override
	public void storeWithStoreException(Customer customer, Order order) throws StoreException {
		jmsTemplate.convertAndSend(MESSAGE_QUEUE,  "JMS received message from /storeWithStoreException : " );
		customerRepository.save(customer);
		orderRepository.save(order);
		throw new StoreException();
	}

// will store in db and send on to queue because we dont rollback the transaction for NoRollbackException.class
	@Transactional(noRollbackFor = NoRollbackException.class, rollbackFor = StoreException.class)
	@Override
	public void storeWithNoRollbackException(Customer customer, Order order) throws NoRollbackException {
		customerRepository.save(customer);
		orderRepository.save(order);
		jmsTemplate.convertAndSend(MESSAGE_QUEUE,  "JMS received message from /storeWithNoRollbackException : " );
		throw new NoRollbackException();
	}

}

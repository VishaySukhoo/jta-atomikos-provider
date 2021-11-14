package com.example.transactionjmsjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.transactionjmsjdbc.jms.Configurations.T_MESSAGE_QUEUE;


@RestController
public class TestConsumerController {
//purpose of this method is just to see the that if something fails, it will not take it off the queue but will try to resend
	@Autowired
	private JmsTemplate jmsTemplate;
	@RequestMapping("/test")
	@Transactional
	public ResponseEntity<Void> test() {
		jmsTemplate.convertAndSend(T_MESSAGE_QUEUE,  "JMS test received message : " );
		return ResponseEntity.ok().build();
	}
}

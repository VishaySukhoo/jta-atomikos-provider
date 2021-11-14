package com.example.transactionjmsjdbc.jms;

import com.example.transactionjmsjdbc.domain.customer.Customer;
import com.example.transactionjmsjdbc.repository.customer.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.transaction.Transactional;

import static com.example.transactionjmsjdbc.jms.Configurations.T_MESSAGE_QUEUE;


@Slf4j
@Component
public class TConsumerListener {

  @Autowired
  private CustomerRepository customerRepository;


  @Transactional
  @JmsListener(destination = T_MESSAGE_QUEUE)
  public void receiveMessage(@Payload String payload,
      @Headers MessageHeaders headers,
      Message message, Session session) {

    log.info("##################################");
    log.info("received payload: <" + payload + ">");
    log.info("##################################");

    Customer customer = new Customer();
    customer.setName("test");
    customerRepository.save(customer);
    throw new RuntimeException();

  }
}

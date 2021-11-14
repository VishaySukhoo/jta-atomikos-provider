package com.example.transactionjmsjdbc.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;

import static com.example.transactionjmsjdbc.jms.Configurations.MESSAGE_QUEUE;


@Slf4j
@Component
public class StoreConsumer {

  @JmsListener(destination = MESSAGE_QUEUE)
  public void receiveMessage(@Payload String payload,
      @Headers MessageHeaders headers,
      Message message, Session session) {

    log.info("##################################");
    log.info("received payload: <" + payload + ">");
    log.info("##################################");
  }
}

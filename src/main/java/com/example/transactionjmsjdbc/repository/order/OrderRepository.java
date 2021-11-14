package com.example.transactionjmsjdbc.repository.order;

import com.example.transactionjmsjdbc.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}

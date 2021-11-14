package com.example.transactionjmsjdbc.repository.customer;

import com.example.transactionjmsjdbc.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

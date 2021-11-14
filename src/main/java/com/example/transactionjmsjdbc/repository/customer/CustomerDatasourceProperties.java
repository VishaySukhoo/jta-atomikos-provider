package com.example.transactionjmsjdbc.repository.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "customer.datasource")
public class CustomerDatasourceProperties {

	private String url;

	private String username;

	private String password;

	private String driverClassName;


}

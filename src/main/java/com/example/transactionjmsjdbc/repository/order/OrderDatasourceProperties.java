package com.example.transactionjmsjdbc.repository.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "order.datasource")
public class OrderDatasourceProperties {
	private String url;

	private String username;

	private String password;

	private String driverClassName;

}

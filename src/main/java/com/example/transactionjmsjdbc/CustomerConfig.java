package com.example.transactionjmsjdbc;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.example.transactionjmsjdbc.repository.customer.CustomerDatasourceProperties;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
//@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.example.transactionjmsjdbc.repository.customer",
		entityManagerFactoryRef = "customerEntityManager"
)
@EnableConfigurationProperties(CustomerDatasourceProperties.class)
public class CustomerConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private CustomerDatasourceProperties customerDatasourceProperties;

	@Bean(name = "customerDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource customerDataSource() throws SQLException {

		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(customerDatasourceProperties.getUrl());
		mysqlXaDataSource.setPassword(customerDatasourceProperties.getPassword());
		mysqlXaDataSource.setUser(customerDatasourceProperties.getUsername());
 		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);


// 		Uncomment below and comment above to use postgres database
//		PGXADataSource pgxaDataSource = new PGXADataSource();
//		pgxaDataSource.setUrl(customerDatasourceProperties.getUrl());
//		pgxaDataSource.setPassword(customerDatasourceProperties.getPassword());
//		pgxaDataSource.setUser(customerDatasourceProperties.getUsername());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads1");
		return xaDataSource;

//		Uncomment below and everything above to use hsqldb
//		AtomikosNonXADataSourceBean dataSourceBean = new AtomikosNonXADataSourceBean();
//		dataSourceBean.setUrl(customerDatasourceProperties.getUrl());
//		dataSourceBean.setPassword(customerDatasourceProperties.getPassword());
//		dataSourceBean.setUser(customerDatasourceProperties.getUsername());
//		dataSourceBean.setDriverClassName(customerDatasourceProperties.getDriverClassName());
//		dataSourceBean.setUniqueResourceName("xads1");
//		return dataSourceBean;
	}

	@Bean(name = "customerEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean customerEntityManager() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(customerDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.example.transactionjmsjdbc.domain.customer");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}


}

package com.example.transactionjmsjdbc;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.example.transactionjmsjdbc.repository.order.OrderDatasourceProperties;
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
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.transactionjmsjdbc.repository.order",
		entityManagerFactoryRef = "orderEntityManager"
)
@EnableConfigurationProperties(OrderDatasourceProperties.class)
public class OrderConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private OrderDatasourceProperties orderDatasourceProperties;

	@Bean(name = "orderDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource orderDataSource() throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(orderDatasourceProperties.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(orderDatasourceProperties.getPassword());
		mysqlXaDataSource.setUser(orderDatasourceProperties.getUsername());

//		Uncomment below and comment above to use postgres database
//		PGXADataSource pgxaDataSource = new PGXADataSource();
//		pgxaDataSource.setUrl(orderDatasourceProperties.getUrl());
//		pgxaDataSource.setPassword(orderDatasourceProperties.getPassword());
//		pgxaDataSource.setUser(orderDatasourceProperties.getUsername());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads2");
		return xaDataSource;

//		Uncomment below and everything above to use hsqldb
//		AtomikosNonXADataSourceBean dataSourceBean = new AtomikosNonXADataSourceBean();
//		dataSourceBean.setUrl(orderDatasourceProperties.getUrl());
//		dataSourceBean.setPassword(orderDatasourceProperties.getPassword());
//		dataSourceBean.setUser(orderDatasourceProperties.getUsername());
//		dataSourceBean.setDriverClassName(orderDatasourceProperties.getDriverClassName());
//		dataSourceBean.setUniqueResourceName("xads2");
//		return dataSourceBean;
	}

	@Bean(name = "orderEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean orderEntityManager() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(orderDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.example.transactionjmsjdbc.domain.order");
		entityManager.setPersistenceUnitName("orderPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}

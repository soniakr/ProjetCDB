package com.excilys.java.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.formation.cbd.persistence" , "com.excilys.formation.cbd.service" , "com.excilys.formation.cbd.ui"})

public class SpringConfiguration {	
		 
		 @Bean
			public HikariDataSource dataSource() {
				HikariConfig config = new HikariConfig("/datasource.properties");
				return new HikariDataSource(config);
			}

			@Bean
			public JdbcTemplate jdbcTemplate() {
				return new JdbcTemplate(dataSource());
			}
	

}

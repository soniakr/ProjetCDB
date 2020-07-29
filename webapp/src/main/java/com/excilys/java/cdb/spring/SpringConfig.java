package com.excilys.java.cdb.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.java.cdb.service", "com.excilys.java.cdb.persistence" , "com.excilys.java.cdb.controllers"})
public class SpringConfig implements  WebApplicationInitializer {

	 @Override
		public void onStartup(ServletContext servletContext) throws ServletException {
			AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
			webContext.register(SpringConfig.class,SpringMvcConfig.class);
			webContext.setServletContext(servletContext);
			ServletRegistration.Dynamic servlet = servletContext.addServlet("dynamicServlet", new DispatcherServlet(webContext));
			servlet.setLoadOnStartup(1);
			servlet.addMapping("/");
		}
	 
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

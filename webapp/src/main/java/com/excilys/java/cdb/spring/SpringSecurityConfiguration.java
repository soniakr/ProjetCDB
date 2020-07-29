package com.excilys.java.cdb.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userService;
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests().antMatchers("/login").permitAll()
	        .antMatchers("/RegisterUser").permitAll()
	        .antMatchers(HttpMethod.GET,"/","/ListComputers").hasAnyRole("USER","ADMIN")
	        .antMatchers(HttpMethod.POST,"/","/ListComputers").hasRole("ADMIN")
	        .antMatchers("/AddComputer").hasRole("ADMIN")
	        .antMatchers("/EditComputer").hasRole("ADMIN")
	        .and()
	        .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/ListComputer").failureUrl("/login?error=true").permitAll()
	        .and()
	        .logout().logoutUrl("/logout").clearAuthentication(true)
	        .and().csrf().disable();
	    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}

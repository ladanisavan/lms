package com.sl.lms.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityConfigurationTest extends WebSecurityConfigurerAdapter{


	/*@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
			auth
				.inMemoryAuthentication()
					.withUser("admin").password("password").roles("ADMIN")
					.and().withUser("user").password("password").roles("USER");
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			authorizeRequests()
				.antMatchers("/loginfailed").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN").anyRequest()
				.authenticated().and().csrf().disable()
				
				.formLogin()
				.loginPage("/login").permitAll()
				.failureUrl("/login?error=true")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/vendor/**", "/css/**", "/js/**", "/images/**");
	}
}

package com.sl.lms.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sl.lms.configuration.auth.handler.LoginFailureHandler;
import com.sl.lms.configuration.auth.handler.LoginSuccessHandler;
import com.sl.lms.configuration.auth.helper.LoginAuthHelper;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
	@Autowired
	LoginSuccessHandler successHandler;
	
	@Autowired
	LoginFailureHandler failureHandler;
	
	@Autowired
	LoginAuthHelper loginAuthHelper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
			authorizeRequests()
				.antMatchers("/loginfailed").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("ADMIN").anyRequest()
				.authenticated().and().csrf().disable()
				
				.formLogin()
				.loginPage("/login").permitAll()
				.failureUrl("/login?error=true")
				.failureHandler(failureHandler)
				.successHandler(successHandler)
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				
				.oauth2Login()
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.userInfoEndpoint()
				.userAuthoritiesMapper(this.userAuthoritiesMapper()).and()
				.loginPage("/login").permitAll()
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
	
	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository);
	}
	
	private GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			return loginAuthHelper.handlePreAuthConditions(authorities);
		};
	}
}

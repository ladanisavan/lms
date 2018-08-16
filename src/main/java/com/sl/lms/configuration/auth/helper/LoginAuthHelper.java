package com.sl.lms.configuration.auth.helper;

import static com.sl.lms.constant.Constants.DOMAIN_KEY;
import static com.sl.lms.constant.Constants.EMAIL_KEY;
import static com.sl.lms.constant.Constants.REQUIRED_DOMAIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.sl.lms.exception.LMSAuthenticationException;
import com.sl.lms.service.EmployeeService;

@Component
public class LoginAuthHelper {

	Logger LOGGER = LoggerFactory.getLogger(LoginAuthHelper.class);

	@Value("${lms.admin.users}")
	private String adminUsersStr;
	
	private EmployeeService employeeService;
	
	public LoginAuthHelper(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	private static List<String> adminUsers = new ArrayList<>();
	
	public Set<GrantedAuthority> handlePreAuthConditions(Collection<? extends GrantedAuthority> authorities) {
		Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
		authorities.forEach(authority -> {
			if (OidcUserAuthority.class.isInstance(authority)) {
				OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

				OidcIdToken idToken = oidcUserAuthority.getIdToken();
				OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
				Map<String, Object> attributes = oidcUserAuthority.getAttributes();

				LOGGER.debug("login request domain: {}",attributes.get(DOMAIN_KEY));
				LOGGER.debug("login request email: {}",attributes.get(EMAIL_KEY));
				LOGGER.debug("login request given_name: {}",attributes.get("given_name"));
				LOGGER.debug("login request family_name: {}",attributes.get("family_name"));
				
				if (REQUIRED_DOMAIN.equals(attributes.get(DOMAIN_KEY))) {
					if(employeeService.isEmployeeExists(String.valueOf(attributes.get(EMAIL_KEY)), true)) {
						if (adminUsers.contains(attributes.get(EMAIL_KEY))) {
							mappedAuthorities.add(new OidcUserAuthority(ADMIN_ROLE, idToken, userInfo));
							mappedAuthorities.add(new OidcUserAuthority(USER_ROLE, idToken, userInfo));

						} else {
							mappedAuthorities.add(new OidcUserAuthority(USER_ROLE, idToken, userInfo));
						}
					}else {
						throw new LMSAuthenticationException("User not found! Please contact HR.");
					}
					
				} else {
					throw new LMSAuthenticationException("Only login from Niviouds domain allowed!");
				}
			} else {
				// throw exception for non openID oauth client
			}
		});
		return mappedAuthorities;
	}
	
	@PostConstruct
	private void parseAdminUsers() {
		if(!Strings.isNullOrEmpty(adminUsersStr)) {
			adminUsers = Arrays.asList(adminUsersStr.split(","));
			LOGGER.debug("configured admin users: {}",adminUsers);
		}
	}
	
	private final String ADMIN_ROLE = "ADMIN";
	private final String USER_ROLE = "USER";
}

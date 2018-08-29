package com.sl.lms.configuration.auth.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sl.lms.constant.URLConstants;
import com.sl.lms.domain.repository.EmployeeRepository;
import com.sl.lms.domain.repository.UserRepository;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	protected Logger LOGGER = LoggerFactory.getLogger(LoginSuccessHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private UserRepository userRepo;
	private EmployeeRepository empRepo;
	
	public LoginSuccessHandler(UserRepository userRepo, EmployeeRepository empRepo) {
		this.userRepo = userRepo;
		this.empRepo = empRepo;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		handle(request, response, authentication);
		saveCurrentLoginUserDetails(request, authentication);
		clearAuthenticationAttributes(request);
		LOGGER.debug("login successful for user: {}",authentication.getName());
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);
		if (response.isCommitted()) {
			LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	//this can be leveraged to set different landing page
	protected String determineTargetUrl(Authentication authentication) {
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (isAdmin) {
			return URLConstants.HOME_URL; // admin/home
		} else {
			return URLConstants.HOME_URL;
		}
	}
	
	protected void saveCurrentLoginUserDetails(HttpServletRequest request, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Optional<String> fullName = Optional.empty();
			String userName = null;
			if(authentication.getPrincipal() instanceof User) {
				userName = ((User)authentication.getPrincipal()).getUsername();
				fullName = userRepo.getUserFullName(userName);
			}else if(authentication.getPrincipal() instanceof DefaultOidcUser) {
				userName = ((DefaultOidcUser)authentication.getPrincipal()).getEmail();
				fullName = empRepo.getEmployeeFullName(userName);
			}
			session.setAttribute("currentUserEmail", userName);
			LOGGER.debug("setting session attribute currentUserEmail: {}",userName);
			if(fullName.isPresent()) {
				session.setAttribute("currentUserFullName", fullName.get());
				LOGGER.debug("setting session attribute currentUserFullName: {}",fullName.get());
			}
		}
	}
	

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
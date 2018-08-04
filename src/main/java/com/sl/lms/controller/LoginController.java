package com.sl.lms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sl.lms.configuration.auth.handler.LoginSuccessHandler;
import com.sl.lms.constant.URLConstants;

@Controller
public class LoginController {

	private final LoginSuccessHandler loginSuccessHandler;
	
	public LoginController(LoginSuccessHandler loginSuccessHandler) {
		this.loginSuccessHandler = loginSuccessHandler;
	}
	
	@GetMapping(URLConstants.ROOT_URL)
	public void getHome(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
	}
	
	@GetMapping(URLConstants.LOGIN_URL)
	public String getLogin() {
		return "login";
	}
	
	@GetMapping(URLConstants.LOGIN_FAILD_URL)
	public String getLoginWhenAuthFailed(final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(AUTH_ERROR, true);
		return "redirect:login";
	}
	
	@GetMapping(URLConstants.HOME_URL)
	public String getUserHome(){
		return "home";
	}
	
	@GetMapping(URLConstants.ACCESS_DENIED_URL)
	public String accessDenied(){
		return "access-denied";
	}
	
	/*@GetMapping("/admin/home")
	public String getAdminHome(){
		return "/admin/home";
	}*/
	
	private static final String AUTH_ERROR = "authError";
}

package com.sl.lms.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

	
	@GetMapping("/user")
	public Principal getUser(Principal principal) {
		return principal;
	}
	
	/*@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@RequestMapping("/logout")
	public String logout(Principal principal, HttpServletRequest request, HttpServletResponse response) throws IOException {

	    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
	    OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
	    consumerTokenServices.revokeToken(accessToken.getValue());

	   // String redirectUrl = getLocalContextPathUrl(request)+"/logout?myRedirect="+getRefererUrl(request);

	    return "redirect: /login";
	}*/
}

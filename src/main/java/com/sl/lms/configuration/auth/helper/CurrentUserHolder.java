package com.sl.lms.configuration.auth.helper;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value=WebApplicationContext.SCOPE_SESSION)
public class CurrentUserHolder{

	private HttpSession session;
	
    public CurrentUserHolder(HttpSession session) {
    	this.session = session;
    }

    public String currentUserEmail() {
        return (String)session.getAttribute("currentUserEmail");
    }

}

package com.techvisio.einstitution.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.flushBuffer();
	}

}

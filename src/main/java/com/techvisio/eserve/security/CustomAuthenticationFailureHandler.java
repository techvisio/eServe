package com.techvisio.eserve.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    //private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    private String defaultFailureUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

       // logger.info("Authentication Failed...", exception);
        String expMessage = exception.getMessage();

//        if (expMessage != null && expMessage.contains(":")) {
//            expMessage = expMessage.substring(0, expMessage.indexOf(":"));
//        }
        String message = "&msg=" + expMessage;

        String redirctUrl = defaultFailureUrl + message;

        //logger.debug("Redirecting to " + redirctUrl);

        getRedirectStrategy().sendRedirect(request, response, redirctUrl);

        // response.sendError(HttpServletResponse.SC_FORBIDDEN, expMessage);
        return;

    }

    /**
     * The URL which will be used as the failure destination.
     * 
     * @param defaultFailureUrl
     *            the failure URL, for example "/loginFailed.jsp".
     */
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'" + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }

}

package zac.demo.hello.security;

import com.duosecurity.Client;
import com.duosecurity.exception.DuoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import zac.demo.hello.config.TwoFactorClient;

import java.io.IOException;

class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        try {

            String state = TwoFactorClient.getInstance().generateState();
            String authUrl = TwoFactorClient.getInstance().createAuthUrl("username", state);
            ((HttpServletResponse) response).sendRedirect(authUrl);

        } catch (DuoException e) {
            throw new RuntimeException(e);
        }


    }
}

package zac.demo.hello.security;

import com.duosecurity.Client;
import com.duosecurity.exception.DuoException;
import com.duosecurity.model.HealthCheckResponse;
import com.duosecurity.model.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.ModelAndView;
import zac.demo.hello.HelloApplication;
import zac.demo.hello.config.TwoFactorClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.info("CALL dofilter ----  - - - - - - - - -");
        logger.info(request.toString());
        chain.doFilter(request, response);


    }
}

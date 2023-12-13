package zac.demo.hello.security;

import com.duosecurity.exception.DuoException;
import com.duosecurity.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zac.demo.hello.config.TwoFactorClient;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ValidationInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

    public static Map<String, String> parseQueryString(String query) throws URISyntaxException {
        Map<String, String> queryPairs = new HashMap<>();

        try {
            String[] pairs = query.split("&");


            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8.name()),
                        URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8.name()));
            }
        } catch (Exception e) {
            logger.info("Parse ERROR ");
        }

        return queryPairs;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Your validation logic here
        logger.info("ZAC ___ PRE PRE PRE PRE ");
        logger.info(request.getRequestURI());
        logger.info(request.getQueryString());
        HttpSession session = request.getSession();
        LocalDateTime expiryTime = (LocalDateTime) session.getAttribute("duoSessionExpireTime");
        if ( expiryTime != null && LocalDateTime.now().isAfter(expiryTime) ) {
            session.invalidate();
            response.sendRedirect("/login?logout");
            return true;
        }

        if (session.getAttribute("duoSuccess") == "true")

            return true;

        Map<String, String> queryParams = parseQueryString(request.getQueryString());

        String duoCode = queryParams.get("duo_code");

        try {
            if ( ! duoCode.isBlank()) {
                Token token = TwoFactorClient.getInstance().exchangeAuthorizationCodeFor2FAResult(duoCode, "username");
                logger.info(token.toString());

                session.setAttribute("duoSuccess", "true");
                session.setAttribute("duoSessionExpireTime", LocalDateTime.now().plusSeconds(30));
                return true;
            }
        } catch (DuoException e) {
            logger.info("ERROR ROROR OR O RO RO OO");
            logger.info(e.getMessage());
        }
        return false; // Return true to continue the request, false to block it
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Post-request logic
        logger.info("ZAC ___POST PSOT PSOT  ");
        logger.info(request.getRequestURI());
        logger.info(request.getQueryString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Cleanup logic if needed
        logger.info("CLEAN UID OD DL LD LD LD L L__POST PSOT PSOT  ");

    }
}

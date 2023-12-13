package zac.demo.hello.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class ErrorHandlingConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error.html"));
    }

    @Controller
    public static class CustomErrorController implements ErrorController {

        private static final String PATH = "/error";

        @RequestMapping(value = PATH)
        public String error() {
            return "error.html";
        }


        public String getErrorPath() {
            return PATH;
        }
    }
}

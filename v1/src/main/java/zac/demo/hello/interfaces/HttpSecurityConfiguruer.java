package zac.demo.hello.interfaces;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@FunctionalInterface
public interface HttpSecurityConfiguruer {

    void configure(HttpSecurity http) throws Exception;

}

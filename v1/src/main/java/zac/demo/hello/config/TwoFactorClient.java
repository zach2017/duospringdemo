package zac.demo.hello.config;

import com.duosecurity.Client;
import com.duosecurity.exception.DuoException;
import com.duosecurity.model.HealthCheckResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import zac.demo.hello.properties.ZacProperties;
import zac.demo.hello.security.CustomFilter;

@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:zach.properties")
@ConditionalOnProperty(name = "zac.enabled", matchIfMissing = true)
@EnableConfigurationProperties(ZacProperties.class)
public class TwoFactorClient {
    // Private static instance of the class, initialized lazily
    private static Client instance;
    private final static Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    private TwoFactorClient() {
    }

    // Public static method to access the instance
    public static synchronized Client getInstance() throws DuoException {
        if (instance == null) {
            String duoapi = System.getenv("DUOAPI");
            String duoid = System.getenv("DUOID");
            String duosecret = System.getenv("DUOSECRET");
            String duoredirect = System.getenv("DUOREDIRECT");

            logger.info("XX CLIENT DUO DODOD O DO  L AS LA LSLL");
            logger.info(duoid);

            instance = new Client.Builder(duoid, duosecret, duoapi, duoredirect).build();


        } else {
            logger.info(instance.healthCheck().toString());
        }

        return instance;
    }

    // Other methods of the class
}

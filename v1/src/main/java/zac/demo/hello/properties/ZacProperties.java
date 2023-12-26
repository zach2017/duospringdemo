package zac.demo.hello.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "zac.props")
@Validated
public record ZacProperties(@DefaultValue("false") boolean usezac) {
}
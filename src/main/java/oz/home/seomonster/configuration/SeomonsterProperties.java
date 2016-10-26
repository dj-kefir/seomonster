package oz.home.seomonster.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix="seomonster")
@Component
public class SeomonsterProperties {

    private String proxyFileName;
    private int repeatDelayInSec;
    private Anticaptcha anticaptcha;
    private Serp serp;

    @Data
    public static class Anticaptcha {
        String secretKey;
    }

    @Data
    public static class Serp {
        String yaSerpItemClassname;
        String captchaTagClassName;
    }

}

package oz.home.seomonster.service;

import lombok.Getter;
import org.springframework.stereotype.Component;
import oz.home.seomonster.model.Proxy;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SetupData {

    private String proxyFileName = "/proxies.csv";

    @Getter
    List<Proxy> proxies;

    @PostConstruct
    private void init() {
        setupProxies();
    }

    private void setupProxies() {
        proxies = CsvUtils.loadObjectList(Proxy.class, proxyFileName);
    }
}

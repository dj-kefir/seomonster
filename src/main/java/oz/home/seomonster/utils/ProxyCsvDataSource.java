package oz.home.seomonster.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import oz.home.seomonster.model.Proxy;
import oz.home.seomonster.utils.CsvUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ProxyCsvDataSource {

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

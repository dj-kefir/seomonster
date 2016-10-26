package oz.home.seomonster.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import oz.home.seomonster.model.Proxy;
import oz.home.seomonster.service.ProxyService;
import oz.home.seomonster.utils.CsvUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CsvProxyServiceImpl implements ProxyService {

    @Value("${seomonster.proxyFileName}")
    private String proxyFileName;
    private List<Proxy> proxies;

    @Override
    public List<Proxy> getProxies() {
        return proxies;
    }

    @Override
    public Proxy getNewProxy() {

        for (Proxy p : proxies) {
            LocalDateTime now = LocalDateTime.now();
            if ((p.getLastUse() == null) || (now.plusSeconds(30).isAfter(p.getLastUse()))) {
                return p;
            }
        }
        return null;
    }

    @PostConstruct
    public void reloadProxies() {
        this.proxies = CsvUtils.loadObjectList(Proxy.class, proxyFileName);
    }
}

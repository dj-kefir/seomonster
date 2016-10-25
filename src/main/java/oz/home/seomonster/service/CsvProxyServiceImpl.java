package oz.home.seomonster.service;

import oz.home.seomonster.model.Proxy;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 24.10.2016.
 * Eldorado LLC
 */
public class CsvProxyServiceImpl implements ProxyService {

    private String proxyFileName = "/proxies.csv";
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

    public CsvProxyServiceImpl() {
        setupProxies();
    }


    private void setupProxies() {
        this.proxies = CsvUtils.loadObjectList(Proxy.class, proxyFileName);
    }
}

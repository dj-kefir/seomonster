package oz.home.seomonster.service;

import oz.home.seomonster.model.Proxy;

import java.util.List;

/**
 * Created by Igor Ozol
 * on 24.10.2016.
 * Eldorado LLC
 */
public interface ProxyService {

    List<Proxy> getProxies();

    Proxy getNewProxy();

 }

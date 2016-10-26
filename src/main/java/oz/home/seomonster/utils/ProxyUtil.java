package oz.home.seomonster.utils;

import oz.home.seomonster.model.Proxy;

public class ProxyUtil {

    public static Proxy createProxy() {
        return Proxy.builder()
                .host("185.118.66.88")
                .port(16015)
                .login("ystal")
                .password("gzsdfjn")
                .schema("http").build();
    }
}

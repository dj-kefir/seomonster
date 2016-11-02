package oz.home.seomonster.deleted;

import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * Created by ozol on 02.11.2016.
 */
public class HttpProxyServer {
    public static void main(String[] args) {
        DefaultHttpProxyServer.bootstrap()
                .withPort(8080)
                .start();
    }
}

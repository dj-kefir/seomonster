package oz.home.seomonster.deleted;

import sockslib.server.SocksProxyServer;
import sockslib.server.SocksServerBuilder;

import java.io.IOException;

/**
 * Created by Ozol on 27.10.2016.
 */
public class SocksServer {

    public static void main(String[] args) throws IOException {
        SocksProxyServer proxyServer = SocksServerBuilder.buildAnonymousSocks5Server();
        proxyServer.start();// Creat a SOCKS5 server bind at port 1080

    }
}

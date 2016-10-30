package oz.home.seomonster.socks;

import sockslib.common.methods.UsernamePasswordMethod;
import sockslib.server.SocksProxyServer;
import sockslib.server.SocksServerBuilder;
import sockslib.server.manager.MemoryBasedUserManager;
import sockslib.server.manager.UserManager;

import java.io.IOException;

public class ProtectedSocksServer {

    public static void main(String[] args) throws IOException {
        UserManager userManager = new MemoryBasedUserManager();
        userManager.addUser("fucksocks", "fucksocks");
        SocksProxyServer proxyServer = SocksServerBuilder.newSocks5ServerBuilder()
                .setUserManager(userManager)
                .addSocksMethods(new UsernamePasswordMethod())
                .build();
        proxyServer.start();
    }
}

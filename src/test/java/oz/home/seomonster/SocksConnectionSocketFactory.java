package oz.home.seomonster;

import org.apache.http.HttpHost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Igor Ozol
 * on 27.10.2016.
 * Eldorado LLC
 */
public class SocksConnectionSocketFactory implements ConnectionSocketFactory {
    @Override
    public Socket createSocket(HttpContext context) throws IOException {
        //185.118.66.88:32015
        InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);

        java.net.Authenticator authenticator = new java.net.Authenticator() {

            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication("ystal", "gzsdfjn".toCharArray());
            }
        };

        System.setProperty("java.net.socks.username", "ystal");
        System.setProperty("java.net.socks.password", "gzsdfjn");
        java.net.Authenticator.setDefault(authenticator);


        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpContext context) throws IOException {
        Socket sock;
        if (socket != null) {
            sock = socket;
        } else {
            sock = createSocket(context);
        }
        if (localAddress != null) {
            sock.bind(localAddress);
        }
        try {
            //sock.connect(remoteAddress, connectTimeout);
            sock.connect(remoteAddress, 5*1000);
        } catch (SocketTimeoutException ex) {
            throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
        }
        return sock;
    }
}

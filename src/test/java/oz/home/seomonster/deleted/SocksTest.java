package oz.home.seomonster.deleted;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import oz.home.seomonster.SocksConnectionSocketFactory;

import java.net.InetSocketAddress;

/**
 * Created by Igor Ozol
 * on 27.10.2016.
 * Eldorado LLC
 */
public class SocksTest {

    public static void main(String[] args)throws Exception {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SocksConnectionSocketFactory())
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress("185.118.66.88", 32015);
            //InetSocketAddress socksaddr = new InetSocketAddress("31.208.100.153", 45554);

            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            HttpHost target = new HttpHost("yandex.ru", 80, "https");
            HttpGet request = new HttpGet("/");
            request.addHeader("Content-Type","application/xml;charset=UTF-8");
           // System.out.println("Executing request " + request + " to " + target + " via SOCKS proxy " + socksaddr);
            CloseableHttpResponse response = httpclient.execute(target, request, context);
           // CloseableHttpResponse response = httpclient.execute(request, context);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}

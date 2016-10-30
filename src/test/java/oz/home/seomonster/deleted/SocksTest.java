package oz.home.seomonster.deleted;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Properties;

/**
 * Created by Igor Ozol
 * on 27.10.2016.
 * Eldorado LLC
 */
public class SocksTest {

    public static void main(String[] args)throws Exception {

        testUrlConnection2();
        if (true) return;
        testUrlConnetcion();
        if (true) return;

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new SocksConnectionSocketFactory())
                .register("https", new SocksConnectionSocketFactory())
                .register("socks", new SocksConnectionSocketFactory())
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", 1080);
            //InetSocketAddress socksaddr = new InetSocketAddress("31.208.100.153", 45554);

            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            //HttpHost target = new HttpHost("yandex.ru", 443, "https");
            HttpHost target = new HttpHost("propitka.ru", 80, "http");

//            HttpHost proxy = new HttpHost("185.118.66.88", 3215, "socks");
//            RequestConfig config = RequestConfig.custom()
//                    .setProxy(proxy)
//                    .build();
            HttpGet request = new HttpGet("/");
            request.addHeader("Content-Type","application/xml;charset=UTF-8");
            request.addHeader("Host","propitka.ru");
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

    private static void testUrlConnection2() throws IOException {

//        String url = "http://propitka.ru",
//                proxyAddress = "103.47.125.7",
//                port = "45554";

        String url = "http://propitka.ru",
                proxyAddress = "127.0.0.1",
                port = "1080";

        InetSocketAddress socksaddr = new InetSocketAddress(proxyAddress, Integer.valueOf(port));
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);

        URL server = new URL(url);
        Properties systemProperties = System.getProperties();
//        systemProperties.setProperty("http.proxyHost",proxyAddress);
//        systemProperties.setProperty("http.proxyPort",port);
//        systemProperties.setProperty("java.net.socks.username", "ystal");
//        systemProperties.setProperty("java.net.socks.password", "gzsdfjn");
        java.net.Authenticator authenticator = new java.net.Authenticator() {

            protected java.net.PasswordAuthentication getPasswordAuthentication() {

                // Get information about the request
                String prompt = getRequestingPrompt();
                String hostname = getRequestingHost();
                InetAddress ipaddr = getRequestingSite();
                int port = getRequestingPort();
                System.out.println("hostname = " + hostname);
                System.out.println("getRequestingProtocol() = " + getRequestingProtocol());
                return new java.net.PasswordAuthentication("fucksocks", "fucksocks".toCharArray());
            }
        };
        java.net.Authenticator.setDefault(authenticator);

        HttpURLConnection connection = (HttpURLConnection)server.openConnection(proxy);
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();

    }

    private static void testUrlConnetcion() throws IOException {
        InetSocketAddress socksaddr = new InetSocketAddress("185.118.66.88", 16015);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socksaddr);

        java.net.Authenticator authenticator = new java.net.Authenticator() {

            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication("ystal", "gzsdfjn".toCharArray());
            }
        };

//        System.setProperty("java.net.socks.username", "ystal");
//        System.setProperty("java.net.socks.password", "gzsdfjn");
        java.net.Authenticator.setDefault(authenticator);

        URL propitka = new URL("https://yandexs.ru");
        HttpURLConnection connection = (HttpURLConnection)propitka.openConnection();
       // connection.setRequestMethod("GET");
       // connection.setRequestProperty("Host", "propitka.ru");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();

    }


}

package oz.home.seomonster.deleted;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;

public class Test {

    private static class CustomHttpRoutePlanner implements HttpRoutePlanner {

        @Override
        public HttpRoute determineRoute(HttpHost target, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
            System.err.println("Proxies chain: " + Arrays.toString(proxies));
            return new HttpRoute(target,
                    null,
                    proxies,
                    false,
                    RouteInfo.TunnelType.TUNNELLED,
                    RouteInfo.LayerType.PLAIN);
        }
    }
    public static void main(String[] args) throws IOException {
        //httpPetOverEldoProxy();
        httpGetOverProxyChain();
    }

    private static String ELDO_PROXY_URL = "xxxxxxxxxxxxxxxxxxxxx";
    private static int ELDO_PROXY_PORT = 8080;
    private static String MY_PROXY_URL = "179.189.192.79";
    private static int MY_PROXY_PORT = 8080;
    private static HttpHost[] proxies = {
            new HttpHost("127.0.0.1", 8080),
            new HttpHost(ELDO_PROXY_URL, ELDO_PROXY_PORT)};
            //new HttpHost(MY_PROXY_URL, MY_PROXY_PORT)};

    private static class ProxyChainsRoutePlanner implements HttpRoutePlanner {
        @Override
        public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
            return new HttpRoute(target,
                    null,
                    proxies,
                    false,
                    RouteInfo.TunnelType.PLAIN,
                    RouteInfo.LayerType.PLAIN);
        }
    }

    /*
      Caused by: org.apache.http.HttpException: Proxy chains are not supported.
      Вероятно из-за того что первый прокси не поддерживает цепочкук проксирования
     */
    private static void httpGetOverProxyChain() throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(ELDO_PROXY_URL, ELDO_PROXY_PORT),
                new UsernamePasswordCredentials("iozol", "PXR85qU190"));
//        credsProvider.setCredentials(
//                new AuthScope(MY_PROXY_URL, MY_PROXY_PORT),
//                new UsernamePasswordCredentials("ystal", "passwd"));

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .setRoutePlanner(new ProxyChainsRoutePlanner())
                .build();

        HttpGet httpGet = new HttpGet("http://propitka.ru/");
        HttpResponse response = httpclient.execute(httpGet);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println("Executing request " + result.toString());
    }


    private static void httpPetOverEldoProxy() throws IOException {
        System.setProperty("http.proxyHost", "xxxxxxxxxxxxxxxx");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("http.proxyUser", "xxxxxxxxxxxxxx");
        System.setProperty("http.proxyPassword", "xxxxxxxxxxxxxxxxxx");

        URL url = new URL("http://propitka.ru");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("185.118.66.88", 32015));

        HttpURLConnection conn = (HttpURLConnection)url.openConnection(proxy);
        HttpURLConnection.setFollowRedirects(false);
        conn.setConnectTimeout(5*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}

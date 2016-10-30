package oz.home.seomonster.socks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import sockslib.client.Socks5;
import sockslib.client.SocksProxy;
import sockslib.client.SocksSocket;
import sockslib.common.UsernamePasswordCredentials;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;

@Slf4j
public class SocksClient {

    // http://www.dokwork.ru/2012/02/http-java-tcp.html
    // http://stackoverflow.com/questions/10673684/send-http-request-manually-via-socket
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(new ClassPathResource("stackoverflow.GET").getInputStream(), "utf-8").useDelimiter("\\A");
        final String GET = scanner.next();
        scanner.close();
        log.info("REQUEST: \n" + GET);

        // local socks5 proxy
        SocksProxy localProxy = new Socks5(new InetSocketAddress("127.0.0.1", 1080));
        localProxy.setCredentials(new UsernamePasswordCredentials("fucksocks", "fucksocks"));
        connectThroughSocksProxy(GET, localProxy);

//        // www.socks-proxy.net
//        SocksProxy freeSocks5Proxy = new Socks5(new InetSocketAddress("96.19.139.100", 45554));
//        connectThroughSocksProxy("stackoverflow.com", STACKOVERFLOW_GET, freeSocks5Proxy);

//        // ProxySeller
//        Scanner scanner_ya = new Scanner(new ClassPathResource("yandex.GET").getInputStream(), "utf-8").useDelimiter("\\A");
//        final String YANDEX_GET = scanner_ya.next();
//        scanner.close();
//        SocksProxy proxySeller = new Socks5(new InetSocketAddress("185.118.66.88", 32015));
//        proxySeller.setCredentials(new UsernamePasswordCredentials("ystal", "gzsdfjn"));
//        connectThroughSocksProxy("yandex.ru", YANDEX_GET, proxySeller);

    }

    private static void connect(String request) throws Exception {
        SocksProxy proxy = new Socks5(new InetSocketAddress("127.0.0.1",1080));
        proxy.setCredentials(new UsernamePasswordCredentials("fucksocks", "fucksocks"));

        SocksSocket socket = new SocksSocket(proxy, new InetSocketAddress("propitka.ru",80));
        proxy.setProxySocket(socket);
        proxy.requestConnect(InetAddress.getByName("127.0.0.1"), 1080);
    }

    private static String connectOverPlainJavaSocket(String request) throws Exception {
        String host = "propitka.ru";
        int port = 80;

        /* Отправляется запрос на сервер */
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            System.out.println("Создан сокет: " + host + " port:" + port);
            socket.getOutputStream().write(request.getBytes());
            socket.getOutputStream().flush();
            System.out.println("GET запрос отправлен. \n");
        } catch (Exception e) {
            throw new Exception("Ошибка при отправке запроса: "
                    + e.getMessage(), e);
        }

        /* Ответ от сервера записывается в результирующую строку */
        String res = null;
        try {
            InputStreamReader isr = new InputStreamReader(socket
                    .getInputStream());
            BufferedReader bfr = new BufferedReader(isr);
            StringBuffer sbf = new StringBuffer();
            int ch = bfr.read();
            while (ch != -1) {
                sbf.append((char) ch);
                ch = bfr.read();
            }
            res = sbf.toString();
        } catch (Exception e) {
            throw new Exception("Ошибка при чтении ответа от сервера.", e);
        }
        socket.close();

        log.info(res);
        return res;

    }

    private static void connectThroughSocksProxy(String request, SocksProxy proxy) throws Exception {
        String host = getHost(request);
        SocksSocket socket = new SocksSocket(proxy, new InetSocketAddress(host,80));
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        socket.getOutputStream().write(request.getBytes());
        socket.getOutputStream().flush();
        log.info("\n ************ Запрос отправлен. ***************** \n");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        StringBuffer sbRes = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            sbRes.append(inputLine).append("\r\n");
        in.close();
        log.info(sbRes.toString() + "\n");

        socket.close();
    }

    private static void connectThroughSocksByProxySeller(String request) throws Exception {
        SocksProxy proxy = new Socks5(new InetSocketAddress("185.118.66.88", 32015));
        proxy.setCredentials(new UsernamePasswordCredentials("ystal", "gzsdfjn"));

        SocksSocket socket = new SocksSocket(proxy, new InetSocketAddress("propitka.ru",80));
        socket.getOutputStream().write(request.getBytes());
        log.info("\n ************ Запрос отправлен. ***************** \n");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        StringBuffer sbRes = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            sbRes.append(inputLine).append("\r\n");
        in.close();
        log.info(sbRes.toString() + "\n");

        socket.close();
    }

    /**
     * Возвращает имя хоста (при наличии порта, с портом) из http заголовка.
     */
    private static String getHost(String header) throws ParseException {
        final String host = "Host: ";
        final String normalEnd = "\n";
        final String msEnd = "\r\n";

        int s = header.indexOf(host, 0);
        if (s < 0) {
            return "localhost";
        }
        s += host.length();
        int e = header.indexOf(normalEnd, s);
        e = (e > 0) ? e : header.indexOf(msEnd, s);
        if (e < 0) {
            throw new ParseException(
                    "В заголовке запроса не найдено " +
                            "закрывающих символов после пункта Host.",
                    0);
        }
        String res = header.substring(s, e).trim();
        return res;
    }
}

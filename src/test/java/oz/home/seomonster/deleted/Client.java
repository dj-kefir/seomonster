package oz.home.seomonster.deleted;

import oz.home.seomonster.model.*;
import oz.home.seomonster.utils.ProxyUtil;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.*;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ozol on 26.10.2016.
 */
public class Client {

    // http://stackoverflow.com/questions/1388822/how-can-i-configure-httpclient-to-authenticate-against-a-socks-proxy/1388903#1388903
    // http://madhurtanwani.blogspot.ru/2010/05/using-proxyselector-to-take-control-of.html
    // http://www.rgagnon.com/javadetails/java-0085.html
    // http://stackoverflow.com/questions/22937983/how-to-use-socks-5-proxy-with-apache-http-client-4k
    public static void main(String[] args) throws IOException {

        socks();
        httpProxy();
        //        java.net.Authenticator authenticator = new java.net.Authenticator() {
//            @Override
//            protected java.net.PasswordAuthentication getPasswordAuthentication() {
//                return new java.net.PasswordAuthentication(socksProxyUserName, socksProxyPassword.toCharArray());
//            }
//
//        };

        if (true) return;

        System.setProperty("socksProxyHost", "185.118.66.88");
        System.setProperty("socksProxyPort", "32015");
        System.setProperty("java.net.socks.username", "ystal"); //$NON-NLS-1$
        System.setProperty("java.net.socks.password", "gzsdfjn"); //$NON-NLS-1$
        //java.net.Authenticator.setDefault(authenticator);

        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("185.118.66.88", 32015));
        //Socket socket = new Socket(proxy);

        System.out.println("Begin...");
        URL url = new URL("http://ya.ru");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

        System.out.println("Exit...");


//        InputStream sin = socket.getInputStream();
//        OutputStream sout = socket.getOutputStream();
    }

    private static void socks() throws IOException {
        System.setProperty("socksProxyHost", "185.118.66.88");
        System.setProperty("socksProxyPort", "32015");
        System.setProperty("socksProxyPort", "32015");
        System.setProperty("java.net.socks.username", "ystal"); //$NON-NLS-1$
        System.setProperty("java.net.socks.password", "gzsdfjn"); //$NON-NLS-1$

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new
                        PasswordAuthentication("ystal","gzsdfjn".toCharArray());
            }});

        Proxy p = new Proxy(Proxy.Type.SOCKS,  new InetSocketAddress("185.118.66.88", 32015));
        Socket s = new Socket(p);
        InetSocketAddress addr = InetSocketAddress.createUnresolved("https://ya.ru", 8080);
        s.connect(addr);

    }

    private static void httpProxy() throws IOException {
       // System.setProperty("java.net.useSystemProxies", "true");
//        System.setProperty("http.proxyHost", "185.118.66.88");
//        System.setProperty("http.proxyPort", "1605");
//        System.setProperty("http.proxyHost", "89.165.65.88");
//        System.setProperty("http.proxyPort", "8080");
//        System.setProperty("http.proxyUser", "ystal");
//        System.setProperty("http.proxyPassword", "gzsdfjn");
//        Authenticator.setDefault(new ProxyAuth("ystal", "gzsdfjn"));


        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new
                        PasswordAuthentication("ystal","gzsdfjn".toCharArray());
            }});

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("185.118.66.88", 1605));
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("89.165.65.88", 8080));
        URL url = new URL("https://ya.ru");
        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);

//        HttpURLConnection uc = (HttpURLConnection)url.openConnection();
        uc.connect();

        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line;
        while ((line = in.readLine()) != null){
            tmp.append(line + "\n");
        }
        System.out.println(tmp.toString());

    }

//    public static void main(String[] args) {
//        int serverPort = 7777; // здесь обязательно нужно указать порт к которому привязывается сервер.
//        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
//        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.
//
//        try {
//            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
////            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
////            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
//
////            oz.home.seomonster.model.Proxy modelProxy = ProxyUtil.createProxy();
////            SocketAddress addr = new
////                    InetSocketAddress(modelProxy.getHost(), modelProxy.getPort());
//
////            return oz.home.seomonster.model.Proxy.builder()
////                    .host("185.118.66.88")
////                    .port(16015)
////                    .login("ystal")
////                    .password("gzsdfjn")
////                    .schema("http").build();
//            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("185.118.66.88", 16015));
//            Socket socket = new Socket(proxy);
//
////
////            oz.home.seomonster.model.Proxy proxy = ProxyUtil.createProxy();
////            String proxyHost = proxy.getHost();
////            int proxyPort = proxy.getPort();
////            InetSocketAddress proxyAddr = new InetSocketAddress(proxyHost, proxyPort);
////            Socket underlying = new Socket(new Proxy(Proxy.Type.SOCKS, proxyAddr));
////            underlying.connect(new InetSocketAddress(getHost(), getPort()));
////            socket = (SSLSocket) factory.createSocket(
////                    underlying,
////                    proxyHost,
////                    proxyPort,
////                    true);
//
//            System.out.println("Yes! I just got hold of the program.");
//
//            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
//            InputStream sin = socket.getInputStream();
//            OutputStream sout = socket.getOutputStream();
//
//            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
//            DataInputStream in = new DataInputStream(sin);
//            DataOutputStream out = new DataOutputStream(sout);
//
//            // Создаем поток для чтения с клавиатуры.
//            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
//            String line = null;
//            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
//            System.out.println();
//
//            while (true) {
//                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
//                System.out.println("Sending this line to the server...");
//                out.writeUTF(line); // отсылаем введенную строку текста серверу.
//                out.flush(); // заставляем поток закончить передачу данных.
//                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
//                System.out.println("The server was very polite. It sent me this : " + line);
//                System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
//                System.out.println();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static class ProxyAuth extends Authenticator {
        private PasswordAuthentication auth;

        private ProxyAuth(String user, String password) {
            auth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return auth;
        }
    }
}


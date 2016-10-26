package oz.home.seomonster.deleted;

import oz.home.seomonster.model.*;
import oz.home.seomonster.utils.ProxyUtil;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.*;
import java.net.Proxy;

/**
 * Created by Ozol on 26.10.2016.
 */
public class Client {

    // http://stackoverflow.com/questions/1388822/how-can-i-configure-httpclient-to-authenticate-against-a-socks-proxy/1388903#1388903
    public static void main(String[] args) throws IOException {

//        java.net.Authenticator authenticator = new java.net.Authenticator() {
//            @Override
//            protected java.net.PasswordAuthentication getPasswordAuthentication() {
//                return new java.net.PasswordAuthentication(socksProxyUserName, socksProxyPassword.toCharArray());
//            }
//
//        };

        System.setProperty("socksProxyHost", "185.118.66.88");
        System.setProperty("socksProxyPort", "16015");
        System.setProperty("java.net.socks.username", "ystal"); //$NON-NLS-1$
        System.setProperty("java.net.socks.password", "gzsdfjn"); //$NON-NLS-1$
        //java.net.Authenticator.setDefault(authenticator);

        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("185.118.66.88", 16015));
//        Socket socket = new Socket(proxy);

        URL url = new URL("http://javainside.ru");
        URLConnection conn = url.openConnection(proxy);

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
}


package oz.home.seomonster.deleted;

import sockslib.client.Socks5;
import sockslib.client.SocksProxy;
import sockslib.client.SocksSocket;

import java.io.*;
import java.net.*;

/**
 * Created by Ozol on 27.10.2016.
 */
public class SocksClient {

    public static void main(String[] args) throws Exception {

        //socksProxy();
        proxyNew();
    }

    private static void socksProxy() throws IOException {
        System.setProperty("java.net.socks.username", "ystal");
        System.setProperty("java.net.socks.password", "gzsdfjn");
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new
                        PasswordAuthentication("ystal","gzsdfjn".toCharArray());
            }});

        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("185.118.66.88", 32015));
        URL url = new URL("https://ya.ru");
        HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
        uc.connect();

        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line;
        while ((line = in.readLine()) != null){
            tmp.append(line + "\n");
        }
        System.out.println(tmp.toString());

    }

    public static void proxyNew() throws IOException {

        SocksProxy proxy = new Socks5(new InetSocketAddress("localhost",1080));
        //Socket socket = new SocksSocket(proxy, new InetSocketAddress("whois.internic.net",43));
        //Socket socket = new SocksSocket(proxy, new InetSocketAddress("213.180.204.3",443));
        InputStream sin = proxy.getInputStream();
        OutputStream sout = proxy.getOutputStream();

        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);

        // Создаем поток для чтения с клавиатуры.
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
        System.out.println();

        while (true) {
            line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
            System.out.println("Sending this line to the server...");
            out.writeUTF(line); // отсылаем введенную строку текста серверу.
            out.flush(); // заставляем поток закончить передачу данных.
            line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            System.out.println("The server was very polite. It sent me this : " + line);
            System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
            System.out.println();
        }

    }
}

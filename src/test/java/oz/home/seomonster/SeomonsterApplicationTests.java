package oz.home.seomonster;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.junit.Test;
import oz.home.seomonster.service.ParserUtils;
import oz.home.seomonster.service.impl.YaSerpServiceImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class,  MongoDataAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@RunWith(TestJunit.class)
@Slf4j
public class SeomonsterApplicationTests {

	/**
	 * 80.78.247.58
	 80.78.245.193
	 194.58.118.59
	 109.237.109.180
	 93.170.128.196

	 Логин: ystal
	 Пароль: rtgyujscx89
	 порт http/https 65233
	 порт socks5 65234
	 */

	@Test
	public void connectViaProxyTest() throws URISyntaxException, IOException {

		int proxyPort = 65233;
		String proxyHost = "80.78.247.58";

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("text", "колонка beats pill xl цена"));
		params.add(new BasicNameValuePair("lr", "213"));
		params.add(new BasicNameValuePair("p", "0"));

		BasicCookieStore cookieStore = new BasicCookieStore();

		URI uri = new URIBuilder()
				.setScheme("https")
				.setHost("yandex.ru")
				.setPath("/search")
				.setParameters(params).build();

		HttpClient httpClient = HttpClients.custom()
				.setDefaultCookieStore(new BasicCookieStore())
				.build();

		String response = Executor.newInstance(httpClient)
				.auth(new HttpHost(proxyHost, proxyPort, "https"), "ystal", "rtgyujscx89")
				//.authPreemptiveProxy(new HttpHost(proxyHost, proxyPort))
				.execute(Request.Get(uri.toString())
							.connectTimeout(5000)
							.socketTimeout(5000)
						    .addHeader("Content-Type","application/xml;charset=UTF-8")
							.viaProxy(new HttpHost(proxyHost, proxyPort)))
				.returnContent().asString();

		log.info("Response {}", response);

	}

	public boolean available(String ip, int port) {
		boolean status = false;
		try {
			InetAddress addr = InetAddress.getByName(ip);
			status = addr.isReachable(1000); // 1 second time for response
			log.info("Server {} is online", ip);
		} catch (Exception e) {
			log.error("Server is unavailable");
		}

		return status;
	}

    @Test
	public void parserUtilsTest() throws IOException, URISyntaxException {

        YaSerpServiceImpl serpService = new YaSerpServiceImpl();
        ParserUtils parserUtils = new ParserUtils();
        Document document = parserUtils.getDocumentFromFile("/ya_search_phrase.html");
        serpService.extractSerpItems(document);

    }
}

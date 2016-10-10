package oz.home.seomonster.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import oz.home.seomonster.exceptions.SeoMonsterException;
import oz.home.seomonster.model.SerpItem;
import oz.home.seomonster.service.SerpService;
import ru.fourqube.antigate.AntigateClient;
import ru.fourqube.antigate.AntigateClientBuilder;
import ru.fourqube.antigate.CaptchaStatus;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class YaSerpServiceImpl implements SerpService {

    public final String yaSerpItemClassName = "organic__subtitle";
    public final String CAPTHCA_TAG = "image form__captcha";

    public String gerSerpResponseBySearchPhrase(String searchPhrase) {
        int proxyPort = 65233;
        String proxyHost = "80.78.247.58";

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("text", "колонка beats pill xl цена"));
        params.add(new BasicNameValuePair("lr", "213"));
        params.add(new BasicNameValuePair("p", "0"));

        String response;
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("yandex.ru")
                    .setPath("/search")
                    .setParameters(params).build();

            response = Executor.newInstance()
                    .auth(new HttpHost(proxyHost, proxyPort, "https"), "ystal", "rtgyujscx89")
                    //.authPreemptiveProxy(new HttpHost(proxyHost, proxyPort))
                    .execute(Request.Get(uri.toString())
                            .connectTimeout(5000)
                            .socketTimeout(5000)
                            .addHeader("Content-Type","application/xml;charset=UTF-8")
                            .viaProxy(new HttpHost(proxyHost, proxyPort)))
                    .returnContent().asString(Charset.forName("UTF-8"));

        } catch (Exception e) {
            throw new SeoMonsterException(e);
        }

        log.info("Response {}", response);

        return response;
    }

    @Override
    public String getCaptchaUrl(Document response) {
        return response.select("img.image.form__captcha").first().attr("src");
    }

    @Override
    public boolean isRecognizedCaptcha(String captchaUrl){

        try {
            AntigateClient client = AntigateClientBuilder.create()
                    .setKey("0a8515415c550bba4270787a8bb26b682")
                    .build();

            double balance = client.getBalance();

            String id = client.upload(new URL(captchaUrl));

            CaptchaStatus cs = client.checkStatus(id);
            if (cs.isReady()) {
                String text = cs.getText();
                // если капча не подошла, отправляем отчет об ошибке распознования
                client.reportBad(id);
            }

            return false;
        } catch (Exception e) {
            log.error("Ошибка распознания капчи", e);
            throw new SeoMonsterException("Ошибка распознания капчи", e);
        }
    }


    @Override
    public List<SerpItem> getSerpItems(String searchPhrase) {
        String response = gerSerpResponseBySearchPhrase(searchPhrase);
        return extractSerpItems(response);
    }

    public List<SerpItem> extractSerpItems(String response) {
        Document doc = Jsoup.parse(response);
        return extractSerpItems(doc);
    }

    public List<SerpItem> extractSerpItems(Document doc) {

        Elements elements = doc
                .select("a.link.organic__url.link.link_cropped_no");

        List<Element> elementList = Arrays.asList(elements.toArray(new Element[elements.size()]));

        List<SerpItem> serpItemList = new ArrayList<>();
        elementList.stream().forEach(e -> {
            SerpItem si = SerpItem.builder()
                    .url(URI.create(e.attr("href"))).build();
            serpItemList.add(si);
        });

        serpItemList.stream().forEach((si) -> log.info("{}", si.getUrl()));
        return serpItemList;
    }

}

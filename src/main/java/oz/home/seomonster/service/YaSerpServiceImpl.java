package oz.home.seomonster.service;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.beans.factory.annotation.Autowired;
import oz.home.seomonster.exceptions.SeoMonsterException;
import oz.home.seomonster.model.Captcha;
import oz.home.seomonster.model.Serp;
import oz.home.seomonster.model.SerpItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 16.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class YaSerpServiceImpl extends BaseSerpService {

    public final String YA_SERP_ITEM_CLASSNAME = "organic__subtitle";
    public final String CAPTHCHA_TAG = "image form__captcha";

    @Autowired
    private AnticaptchaService anticaptchaService;


    @Override
    public String sendSerpRequest(String phrase) {

        int proxyPort = 65233;
        String proxyHost = "80.78.247.58";

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("text", phrase));
        params.add(new BasicNameValuePair("lr", "213"));
        params.add(new BasicNameValuePair("p", "0"));

        String response = null;
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

            log.info("Response {}", response);
            return response;
        } catch (Exception e) {
            log.debug("Response {}", response);
            throw new SeoMonsterException(e);
        }
    }

    @Override
    public Serp extractSerp(Document doc) {
        Elements elements = doc
                .select("a.link.organic__url.link.link_cropped_no");

        List<Element> elementList = Arrays.asList(elements.toArray(new Element[elements.size()]));

        List<SerpItem> serpItemList = new ArrayList<>();
        elementList.stream().forEach(e -> {
            SerpItem si = SerpItem.builder()
                    .uri(URI.create(e.attr("href"))).build();
            serpItemList.add(si);
        });

        serpItemList.stream().forEach((si) -> log.info("{}", si.getUri()));

        return (serpItemList.size() >0) ? Serp.builder().serpItems(serpItemList).build(): null;
    }

    @Override
    public String sendCaptcha(Captcha captcha) {
        return null;
    }

    @Override
    public Captcha extractCaptcha(Document doc) {

        try {
            String imageUrl = doc.select("img.image.form__captcha").first().attr("src");
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
//            byte[] bytes = IOUtils.toByteArray(url);
//            String imgAsString64 = Base64.encodeBase64String(bytes);

            return Captcha.builder().image(image).build();

        } catch (Exception e) {
            throw new SeoMonsterException("Cannot extract captcha image from response", e);
        }

    }

    @Override
    public void saveSerps(List<Serp> serps) {

    }

    public List<SerpItem> extractSerpItems(String response) {

        Document doc = Jsoup.parse(response);
        Elements elements = doc
                .select("a.link.organic__url.link.link_cropped_no");

        List<Element> elementList = Arrays.asList(elements.toArray(new Element[elements.size()]));

        List<SerpItem> serpItemList = new ArrayList<>();
        elementList.stream().forEach(e -> {
            SerpItem si = SerpItem.builder()
                    .uri(URI.create(e.attr("href"))).build();
            serpItemList.add(si);
        });

        serpItemList.stream().forEach((si) -> log.info("{}", si.getUri()));
        return serpItemList;
    }
}

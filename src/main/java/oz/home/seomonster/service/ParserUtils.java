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
import oz.home.seomonster.exceptions.SeoMonsterException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class ParserUtils {

    public Document getDocumentFromFile(String path) throws IOException, URISyntaxException {

        URL url = this.getClass().getResource(path);
        File input = new File(url.toURI());
        return Jsoup.parse(input, "UTF-8", "http://yandex.ru/");
    }

}

package oz.home.seomonster.service;

import org.jsoup.nodes.Document;
import oz.home.seomonster.model.SerpItem;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
public interface SerpService {

    String getCaptchaUrl(Document response);

    boolean isRecognizedCaptcha(String captchaUrl) throws MalformedURLException;

    void sendCaptcha(String text);

    List<SerpItem> getSerpItems(String searchPhrase);

}

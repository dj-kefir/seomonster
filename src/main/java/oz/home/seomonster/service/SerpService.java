package oz.home.seomonster.service;

import org.jsoup.nodes.Document;
import oz.home.seomonster.model.Captcha;
import oz.home.seomonster.model.Serp;

import java.util.List;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
public interface SerpService {

    Document getDocument(String response);
    Serp getSerp(String searchPhrase);
    List<Serp> getSerps(List<String> phrases);
    String sendSerpRequest(String phrase);
    Serp extractSerp(Document document);
    String sendCaptcha(Captcha captcha);
    Captcha extractCaptcha(Document document);
    void saveSerps(List<Serp> serps);



//    BaseSerp getSerpItems(String searchPhrase);
//
//    Captcha getCaptcha(Document response);
//
//    BaseSerp getSerpResponse(String phrase);
//    Image extractCaptcha(Document response);
//    BaseSerp handleCaptcha(Captcha captchaSerp);
//
//    default Serp getSerp(String phrase) {
//
//        BaseSerp response = getSerpResponse(phrase);
//
//        boolean isCaptcha = (response instanceof Captcha);
//        while (isCaptcha) {
//            response = handleCaptcha((Captcha)response);
//        }
//
//        return (Serp)response;
//    }

}

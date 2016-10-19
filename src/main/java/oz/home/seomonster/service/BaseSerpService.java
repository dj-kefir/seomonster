package oz.home.seomonster.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import oz.home.seomonster.exceptions.SeoMonsterException;
import oz.home.seomonster.model.BaseSerp;
import oz.home.seomonster.model.Captcha;
import oz.home.seomonster.model.Serp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Ozol
 * on 16.10.2016.
 * Eldorado LLC
 */
public abstract class BaseSerpService implements SerpService {

    public static final int MAX_ATTEMPT_COUNT = 10;

    @Autowired
    protected AnticaptchaService anticaptchaService;

    @Override
    public Document getDocument(String response) {
        return Jsoup.parse(response);
    }

    public final Serp getSerp(String searchPhrase) {

//        String response = sendSerpRequest(searchPhrase);
//        Document doc = Jsoup.parse(response);

        BaseSerp serp = null; //  = extractSerp(doc);
        Document doc = null;

        int attemptCount = MAX_ATTEMPT_COUNT;
        while (true) {
            if (attemptCount < 0) {
                throw new SeoMonsterException("Unrecognized response");
            }

            if (serp instanceof Captcha) {
                Captcha captcha = extractCaptcha(doc);
                captcha = anticaptchaService.evaluateCaptcha(captcha);
                String responseAfterCapture  = sendCaptcha(captcha);
                doc = Jsoup.parse(responseAfterCapture);
            } else {
                String response = sendSerpRequest(searchPhrase);
                doc = Jsoup.parse(response);
            }

            serp = processSerp(doc);

            attemptCount--;
        }
    }

    private boolean isNullOrEmptySerp(Serp serp) {
        return (serp == null || serp.getSerpItems().size() <= 0);
    }

    public final List<Serp> getSerps(List<String> searchPhrases) {
        List<Serp> serps = new ArrayList<>();

        searchPhrases.stream().forEach(phrase -> serps.add(getSerp(phrase)));
        return serps;
    }

    public BaseSerp processSerp(Document document) {

        BaseSerp serp = extractSerp(document);
        boolean isSerp = serp != null;
        if (isSerp) {
            return serp;
        } else {
            return extractCaptcha(document);
        }
    }
}

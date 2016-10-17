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

        String response = sendSerpRequest(searchPhrase);
        Document doc = Jsoup.parse(response);

        int attemptCount = MAX_ATTEMPT_COUNT;
        BaseSerp serp = extractSerp(doc);

        while (isNullOrEmptySerp((Serp)serp)) {
            attemptCount--;
            while (extractCaptcha(doc) != null) {
                Captcha captcha = anticaptchaService.evaluateCaptcha((Captcha)serp);
                String responseAfterCapture  = sendCaptcha(captcha);
                doc = Jsoup.parse(responseAfterCapture);
            }

            if (attemptCount < 0) {
                throw new SeoMonsterException("Unrecognized response");
            }
        }

        return (Serp)serp;
    }

    private boolean isNullOrEmptySerp(Serp serp) {
        return (serp == null || serp.getSerpItems().size() <= 0);
    }

    public final List<Serp> getSerps(List<String> searchPhrases) {
        List<Serp> serps = new ArrayList<>();

        searchPhrases.stream().forEach(phrase -> serps.add(getSerp(phrase)));
        return serps;
    }
}

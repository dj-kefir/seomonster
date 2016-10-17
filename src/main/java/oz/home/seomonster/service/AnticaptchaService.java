package oz.home.seomonster.service;

import oz.home.seomonster.model.Captcha;

/**
 * Created by Igor Ozol
 * on 16.10.2016.
 * Eldorado LLC
 */
public interface AnticaptchaService {

    Captcha evaluateCaptcha(Captcha captcha);
}

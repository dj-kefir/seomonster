package oz.home.seomonster.service.impl;

import org.springframework.stereotype.Component;
import oz.home.seomonster.model.Captcha;
import oz.home.seomonster.service.AnticaptchaService;

@Component
public class AnticaptchaServiceImpl implements AnticaptchaService {
    @Override
    public Captcha evaluateCaptcha(Captcha captcha) {
        throw new UnsupportedOperationException("evaluateCaptcha not realized!");
    }
}

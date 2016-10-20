package oz.home.seomonster.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import oz.home.seomonster.exceptions.SeoMonsterException;
import oz.home.seomonster.model.Captcha;
import oz.home.seomonster.model.Serp;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Igor Ozol
 * on 17.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class BaseSerpServiceTest {

    public static final String EOS = "\\Z";
    @Spy
    //@InjectMocks
    SerpService yaSerpService = new YaSerpServiceImpl();// Mockito.mock(BaseSerpService.class, Mockito.CALLS_REAL_METHODS);

    @Mock
    AnticaptchaService anticaptchaService;

    String yaSerpResponse;
    String yaCaptchaResponse;
    String yaCaptchaUrl = "https://cloud.mail.ru/public/8St2/UTeYsw9mA";

    @Before
    public void init() throws FileNotFoundException, URISyntaxException {

        MockitoAnnotations.initMocks(this);

        // mocking ya.serp response
        URL urlSerpResponse = this.getClass().getResource("/ya_search_phrase.html");
        File input = new File(urlSerpResponse.toURI());
        yaSerpResponse = new Scanner(input).useDelimiter(EOS).next();

        // mocking captcha ya.serp response
        URL urlCaptchaResponse = this.getClass().getResource("/ya_captcha_response.html");
        input = new File(urlCaptchaResponse.toURI());
        yaCaptchaResponse = new Scanner(input).useDelimiter(EOS).next();

    }

    @Test
    public void getSerpNoCaptchaTest() {

        // Given
        doReturn(yaSerpResponse).when(yaSerpService).sendSerpRequest(anyString());

        // When
        Serp serp = yaSerpService.getSerp("колонка beats pill xl цена");

        // Verify
        assertThat("Отсуствует поисковая выдача", serp.getSerpItems(), hasSize(equalTo(10)));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSerpUnrecognizedResponseTest() {

        // Given
        doReturn("Unknown response").when(yaSerpService).sendSerpRequest(anyString());
        thrown.expect(SeoMonsterException.class); //verify exception

        // Then
        Serp serp = yaSerpService.getSerp("колонка beats pill xl цена");

        // Verify
        verify(anticaptchaService, times(BaseSerpService.MAX_ATTEMPT_COUNT)).evaluateCaptcha(any(Captcha.class));

    }

    @Test
    public void getSerpCaptchaSuccessSendingTest() {
        // Given
        ((BaseSerpService)yaSerpService).anticaptchaService = anticaptchaService;
        doReturn(yaCaptchaResponse).when(yaSerpService).sendSerpRequest(anyString());
        doReturn(yaSerpResponse).when(yaSerpService).sendCaptcha(any(Captcha.class));
        doReturn(Captcha.builder().recognizedResult("щипок").build()).when(anticaptchaService).evaluateCaptcha(any(Captcha.class));

        // When
        Serp serp = yaSerpService.getSerp("колонка beats pill xl цена");

        // Verify
        verify(anticaptchaService, times(1)).evaluateCaptcha(any(Captcha.class));
        assertThat("Отсуствует поисковая выдача", serp.getSerpItems(), hasSize(equalTo(10)));
    }
}
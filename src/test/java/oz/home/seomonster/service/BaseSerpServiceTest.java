package oz.home.seomonster.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import oz.home.seomonster.exceptions.SeoMonsterException;
import oz.home.seomonster.model.Serp;
import oz.home.seomonster.model.SerpItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Igor Ozol
 * on 17.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class BaseSerpServiceTest {

    @Spy
    //@InjectMocks
    SerpService yaSerpService = new YaSerpServiceImpl();// Mockito.mock(BaseSerpService.class, Mockito.CALLS_REAL_METHODS);

    @Mock
    AnticaptchaService anticaptchaService;

    String yaSerpResponse;
    String yaCaptcha;
    String yaCaptchaUrl = "https://1.downloader.disk.yandex.ru/disk/f6189a063532a918ca14e31e74269c69c024b596484b0c52d476844cb0fb018e/57fadc56/Z_rtXo_edVW3KMYm2d3L-Epvmfg0_3wACfu57J6HDjyUaMocwhjILj1QMd34U14xK86oaSuSHXmGVO7KibP_rA%3D%3D?uid=0&filename=yandex_captha.gif&disposition=inline&hash=&limit=0&content_type=image%2Fgif&fsize=2940&hid=beb74d97e064aa080c28b8a8c135921e&media_type=image&tknv=v2&etag=670a4b94a2f2560349c4367f2edb931a";

    @Before
    public void init() throws FileNotFoundException, URISyntaxException {

        MockitoAnnotations.initMocks(this);

        // mocking ya.serp response
        URL urlSerpResponse = this.getClass().getResource("/ya_search_phrase.html");
        File input = new File(urlSerpResponse.toURI());
        yaSerpResponse = new Scanner(input).useDelimiter("\\Z").next();

        // mocking captche ya.serp for tests
        URL urlCaptchaResponse = this.getClass().getResource("/ya_captcha_response.html");
        input = new File(urlCaptchaResponse.toURI());
        yaCaptcha = new Scanner(input).useDelimiter("\\Z").next();

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
    }

    @Test
    public void getSerpCaptchaSendingTest() {

    }
}
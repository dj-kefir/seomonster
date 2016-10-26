package oz.home.seomonster.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import oz.home.seomonster.SeoMonsterTestSupport;
import oz.home.seomonster.service.ProxyService;
import oz.home.seomonster.service.SerpService;
import oz.home.seomonster.service.impl.CsvProxyServiceImpl;
import oz.home.seomonster.service.impl.YaSerpServiceImpl;
import oz.home.seomonster.utils.ProxyUtil;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
@Slf4j
public class YaSerpServiceImplTest extends SeoMonsterTestSupport {

    @InjectMocks
    @Autowired
    SerpService serpService; //= new YaSerpServiceImpl();

    @Mock
    ProxyService proxyService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

      //  ((YaSerpServiceImpl)serpService).proxyService = new CsvProxyServiceImpl();
    }

    @Test
    public void sendSerpRequestTest() {
        // Given
        when(proxyService.getNewProxy()).thenReturn(ProxyUtil.createProxy());

        // Then
        serpService.sendSerpRequest("Купить телефон");

        // Verify
        log.info(serpService.sendSerpRequest("Нужна работа"));

    }

//    @Spy
//    YaSerpServiceImpl yaSerpService = new YaSerpServiceImpl();
//
//    String yaSerp;
//    String yaCaptcha;
//    String yaCaptchaUrl = "https://1.downloader.disk.yandex.ru/disk/f6189a063532a918ca14e31e74269c69c024b596484b0c52d476844cb0fb018e/57fadc56/Z_rtXo_edVW3KMYm2d3L-Epvmfg0_3wACfu57J6HDjyUaMocwhjILj1QMd34U14xK86oaSuSHXmGVO7KibP_rA%3D%3D?uid=0&filename=yandex_captha.gif&disposition=inline&hash=&limit=0&content_type=image%2Fgif&fsize=2940&hid=beb74d97e064aa080c28b8a8c135921e&media_type=image&tknv=v2&etag=670a4b94a2f2560349c4367f2edb931a";
//
//    @Before
//    public void init() throws URISyntaxException, FileNotFoundException {
//        MockitoAnnotations.initMocks(this);
//
//        // mocking ya.serp response
//        URL urlSerpResponse = this.getClass().getResource("/ya_search_phrase.html");
//        File input = new File(urlSerpResponse.toURI());
//        yaSerp = new Scanner(input).useDelimiter("\\Z").next();
//
//        // mocking captche ya.serp for tests
//        URL urlCaptchaResponse = this.getClass().getResource("/ya_captcha_response.html");
//        input = new File(urlCaptchaResponse.toURI());
//        yaCaptcha = new Scanner(input).useDelimiter("\\Z").next();
//    }
//
//    @Test
//    public void getSerpItemTest() throws URISyntaxException, IOException {
//        // Given
//       // doReturn(yaSerp).when(yaSerpService).gerSerpResponseBySearchPhrase(anyString());
//
//        // When
//        List<SerpItem> serpItems = yaSerpService.getSerpItems("колонка beats pill xl цена");
//
//        // Verify
//        assertThat("Отсуствует поисковая выдача", serpItems, hasSize(equalTo(10)));
//    }
//
//   // <img class="image form__captcha" style="background: #cfcfcf;" src="https://yandex.ru/captchaimg?aHR0cHM6Ly9uYS5jYXB0Y2hhLnlhbmRleC5uZXQvaW1hZ2U_a2V5PWMyblE4MmtWTDRyUUZ4VnJ0SGkyckE0S1dOcFREMWUz_0/1476039528/a717918b0f01fb8414c5549542ef4756_efd3ffebd2674504739dedeabf164413" alt=""/>
//
//    @Test
//    public void getUrlOfCaptchaTest() {
//        // Given
//        doReturn(yaCaptcha).when(yaSerpService).gerSerpResponseBySearchPhrase(anyString());
//
//        // Then
//        String captchaUrl = yaSerpService.getCaptchaUrl(Jsoup.parse(yaCaptcha));
//
//        // Verify
//        log.info("captcha url = {}", captchaUrl);
//        assertThat("В этом запросе должна быть капча-урл", captchaUrl, is(yaCaptchaUrl));
//    }
//
//    @Test
//    public void isRecognizedCaptchaTest() throws Exception {
//        // Given
//
//        // Then
//
//        // Verify
//        assertThat("Не удалось распознать капчу", yaSerpService.isRecognizedCaptcha(yaCaptchaUrl), is(true));
//    }
}
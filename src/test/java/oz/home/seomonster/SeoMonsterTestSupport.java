package oz.home.seomonster;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import oz.home.seomonster.configuration.SeomonsterProperties;
import oz.home.seomonster.service.SerpService;

import java.net.Proxy;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeoMonsterTestSupport {

    @Autowired
    SeomonsterProperties appProperties;

    @Autowired
    SerpService serpService;

    @Test
    public void test() {

        //Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);

        log.info("Test worked");
    }


}

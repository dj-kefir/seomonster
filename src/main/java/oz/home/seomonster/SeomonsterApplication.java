package oz.home.seomonster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class SeomonsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeomonsterApplication.class, args);
	}

	@RequestMapping(value = "/api/phrases", method = RequestMethod.POST)
	String postSearchPhrases(@RequestBody String request) {
		log.info(request);
		return "{\"message\": \"Всё принято!\"}";
	}
}

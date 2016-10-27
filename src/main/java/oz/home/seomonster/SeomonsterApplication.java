package oz.home.seomonster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@PostMapping("/api/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {

		//storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "{\"message\": \"Файл принят!\"}";
	}
}

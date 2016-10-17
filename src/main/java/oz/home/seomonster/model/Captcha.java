package oz.home.seomonster.model;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

/**
 Элемент поисковой выдачи - картиночная КАПЧА
 */
@Data
@Builder
public class Captcha extends BaseSerp {

    Image image;
    String recognizedResult;

}

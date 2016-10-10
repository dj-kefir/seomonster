package oz.anticaptcha.api.model;

/**
 * Created by Igor Ozol
 * on 11.10.2016.
 * Eldorado LLC
 */

import lombok.Data;

@Data
public class ImageToTextTask implements Task {
    public final String type = "ImageToTextTask";

    String body;
    Boolean phrase;
    Boolean caseSensivity;
    Integer numeric;
    Boolean math;
    Integer minLength;
    Integer maxLength;
}

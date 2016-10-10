package oz.home.seomonster.exceptions;

import lombok.NoArgsConstructor;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
@NoArgsConstructor
public class SeoMonsterException extends RuntimeException {
    public SeoMonsterException(String message) {
        super(message);
    }

    public SeoMonsterException (Throwable cause) {
        super (cause);
    }

    public SeoMonsterException (String message, Throwable cause) {
        super (message, cause);
    }
}

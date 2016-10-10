package oz.home.seomonster.model;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

/**
 * Created by Igor Ozol
 * on 09.10.2016.
 * Eldorado LLC
 */
@Data
@Builder
public class SerpItem {

    Long id;
    URI url;

}

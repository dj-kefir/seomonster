package oz.home.seomonster.model;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

/**
 Обычный элемент поисковой выдачи
 */
@Data
@Builder
public class SerpItem {

    Long id;
    URI uri;
    int position;

}

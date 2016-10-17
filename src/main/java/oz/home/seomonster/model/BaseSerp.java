package oz.home.seomonster.model;

import lombok.Data;
import org.jsoup.nodes.Document;

/**
 * Абстрактный класс ответа поисковой выдачи
 */
@Data
public abstract class BaseSerp {

    String searchPhrase;
    int count;
    Document sourceDocument;

}

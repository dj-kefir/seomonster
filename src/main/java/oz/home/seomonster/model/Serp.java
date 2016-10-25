package oz.home.seomonster.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Serp extends BaseSerp {


    List<SerpItem> serpItems;

}

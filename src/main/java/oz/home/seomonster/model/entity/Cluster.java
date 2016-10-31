package oz.home.seomonster.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Cluster {
    String mainPhrase;
    Map<String, Integer> phraseToCount = new HashMap<>();

    public Integer clusterSize() {
        return phraseToCount.size();
    }
}

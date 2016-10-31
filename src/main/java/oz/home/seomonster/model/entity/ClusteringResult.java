package oz.home.seomonster.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClusteringResult {

    Project project;
    int countClasters;
    int clusteredPhraseCount;
    int totalPhrasesCount;

}

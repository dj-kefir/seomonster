package oz.home.seomonster.service.impl;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import oz.home.seomonster.model.entity.Cluster;
import oz.home.seomonster.model.entity.ClusteringResult;
import oz.home.seomonster.model.entity.Project;
import oz.home.seomonster.service.ClusterService;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class ClusterServiceImplTest extends TestCase {

    ClusterService clusterService = new ClusterServiceImpl();

    public void testDefineCluster() throws Exception {
        // Given
        Cluster бетонныеРвботы = Cluster.builder()
                .mainPhrase("бетонные работы").build();
        Cluster краскаПоБетону = Cluster.builder()
                .mainPhrase("краска по бетону").build();

        // Then
       // ((ClusterServiceImpl)clusterService).defineCluster();
       // Map<String, Integer> phrases = clusterService.pocessPhraseFile(phrasesCsvResource.getFile().toPath());

        // Verify
    }

    public void testCalculateClusters() throws Exception {
        // Given
        final int CLUSTER_COUNT = 3;
        Resource phrasesCsvResource = new ClassPathResource("phrases.csv");

        // Then
        Map<String, Integer> phrases = clusterService.pocessPhraseFile(phrasesCsvResource.getFile().toPath());
        List<Cluster> clusters = clusterService.calculateClusters(phrases);

        // Verify
        assertThat("Должно быть 3 кластера", clusters, hasSize(CLUSTER_COUNT));
    }

    public void testPocessPhraseFile() throws Exception {
        final int TOTAL_PHRASE_COUNT = 101;
        Resource phrasesCsvResource = new ClassPathResource("phrases.csv");
        // Then
        Map<String, Integer> phrasesToCounts = clusterService.pocessPhraseFile(phrasesCsvResource.getFile().toPath());

        // Verify
        assertThat("Файл с поисковыми фразами не прогрузился", phrasesToCounts.size(), is(TOTAL_PHRASE_COUNT));
    }

    public void testRun() throws Exception {
        // Given
        final int EXPECTED_CLUSTERS = 3;
        Project project = Project.builder()
                .id(1L)
                .description("Тестовый проект")
                .name("Тест")
                .file(Paths.get("/phrases.csv")).build();

        // Then
        ClusteringResult result = clusterService.run(project);

        // Verify
        assertThat("", result.getCountClasters(), is(EXPECTED_CLUSTERS));

    }
}
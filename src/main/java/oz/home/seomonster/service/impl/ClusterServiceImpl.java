package oz.home.seomonster.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import oz.home.seomonster.model.Serp;
import oz.home.seomonster.model.entity.Cluster;
import oz.home.seomonster.model.entity.ClusteringResult;
import oz.home.seomonster.model.entity.Project;
import oz.home.seomonster.service.ClusterService;
import oz.home.seomonster.service.SerpService;

import java.nio.file.Path;
import java.util.*;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    SerpService serpService;

    @Override
    public Project createProject() {
        return null;
    }

    @Override
    public void uploadPhraseFile(MultipartFile file, Project project) {

    }

    @Override
    public ClusteringResult run(Project project) {
        Map<String, Integer> phrasesToCounts = pocessPhraseFile(project.getFile());
        List<Cluster> clusters = calculateClusters(phrasesToCounts);

        ClusteringResult result = ClusteringResult.builder()
                .project(project)
                .countClasters(3)
                .totalPhrasesCount(phrasesToCounts.size()).build();
        return result;
    }

    @Override
    public List<Cluster> calculateClusters(Map<String, Integer> phrasesToCounts) {
        List<Cluster> clusters = new ArrayList<>();
        Set<String> searchPhrases = phrasesToCounts.keySet();

        for (String searchPhrase : searchPhrases) {
            Serp serp = serpService.getSerp(searchPhrase);
            defineCluster(clusters, serp);
        }

        return clusters;
    }

    public void defineCluster(List<Cluster> clusters, Serp serp) {

    }

    @Override
    @SneakyThrows
    public Map<String, Integer> pocessPhraseFile(Path file) {
        Map<String, Integer> result = new HashMap<>();

        Scanner scanner = new Scanner(file, "UTF-8");
        scanner.useDelimiter("\r\n");
        while (scanner.hasNext()) {
            String line = scanner.next();
            String[] columns = line.split(";");
            result.put(columns[0], Integer.valueOf(columns[1]));
        }
        return result;
    }
}

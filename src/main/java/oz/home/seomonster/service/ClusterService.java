package oz.home.seomonster.service;

import org.springframework.web.multipart.MultipartFile;
import oz.home.seomonster.model.entity.Cluster;
import oz.home.seomonster.model.entity.ClusteringResult;
import oz.home.seomonster.model.entity.Project;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ClusterService {

    Project createProject();

    void uploadPhraseFile(MultipartFile file, Project project);

    ClusteringResult run(Project project);

    Map<String, Integer> pocessPhraseFile(Path file);

    List<Cluster> calculateClusters(Map<String, Integer> phrasesToCounts);

}

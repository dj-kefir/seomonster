package oz.home.seomonster.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import oz.home.seomonster.model.entity.Project;

/**
 * Created by Ozol on 28.10.2016.
 */
public interface ProjectRepository extends MongoRepository<Project, Long> {
}

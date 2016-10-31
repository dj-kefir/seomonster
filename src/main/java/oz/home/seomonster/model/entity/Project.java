package oz.home.seomonster.model.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @NonNull
    Long id;

    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    Path file;

}

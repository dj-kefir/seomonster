package oz.anticaptcha.api.model;

import lombok.Data;

/**
 * Created by Igor Ozol
 * on 11.10.2016.
 * Eldorado LLC
 */
@Data
public class TaskResponse {
    Integer errorId;
    String errorCode;
    String errorDescription;
    Integer taskId;
}

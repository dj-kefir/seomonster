package oz.anticaptcha.api;

import oz.anticaptcha.api.model.*;

/**
 * Created by Igor Ozol
 * on 11.10.2016.
 * Eldorado LLC
 */
public interface AnticaptchaClient {

    TaskResponse createTask(String clientKey, int softId, Task task, String languagePool);

    TaskResultResponse getTaskResult(String clientKey, String la);

    BalanceResponse getBalance(String clientKey);

    QueueStatsResponse getQueueStats(int queueId);

}

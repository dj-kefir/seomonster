package oz.anticaptcha.api;

import oz.anticaptcha.api.model.*;

/**
 * Created by Igor Ozol
 * on 11.10.2016.
 * Eldorado LLC
 */
public class AnticaptchaClientImpl implements AnticaptchaClient {
    @Override
    public TaskResponse createTask(String clientKey, int softId, Task task, String languagePool) {
        return null;
    }

    @Override
    public TaskResultResponse getTaskResult(String clientKey, String la) {
        return null;
    }

    @Override
    public BalanceResponse getBalance(String clientKey) {
        return null;
    }

    @Override
    public QueueStatsResponse getQueueStats(int queueId) {
        return null;
    }
}

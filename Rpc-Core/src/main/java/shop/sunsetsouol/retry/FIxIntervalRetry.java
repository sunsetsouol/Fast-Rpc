package shop.sunsetsouol.retry;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.extern.slf4j.Slf4j;
import shop.sunsetsouol.model.RpcResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author YinJunBiao
 * @date 2025/2/19 22:27
 * @Description
 */
@Slf4j
public class FIxIntervalRetry implements RetryStrategy{
    @Override
    public <T> T retry(Callable<T> callable) throws ExecutionException, RetryException {
        Retryer<T> retry = RetryerBuilder.<T>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}", attempt.getAttemptNumber());
                    }
                })
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();
        return retry.call(callable);
    }
}

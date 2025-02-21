package shop.sunsetsouol.retry;

import shop.sunsetsouol.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author YinJunBiao
 * @date 2025/2/17 20:23
 * @Description
 */
public interface RetryStrategy {

    <T> T retry(Callable<T> callable) throws Exception;
}

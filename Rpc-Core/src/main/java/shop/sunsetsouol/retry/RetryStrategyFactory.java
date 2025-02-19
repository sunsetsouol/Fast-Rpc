package shop.sunsetsouol.retry;

import shop.sunsetsouol.spi.SPILoader;

/**
 * @author YinJunBiao
 * @date 2025/2/17 20:24
 * @Description
 */
public class RetryStrategyFactory {

    static {
        SPILoader.load(RetryStrategy.class);
    }

    public static RetryStrategy getRetryStrategy(String key) {
        return SPILoader.getInstance(RetryStrategy.class, key);
    }
}

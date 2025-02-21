package shop.sunsetsouol.server;

import shop.sunsetsouol.spi.SPILoader;

/**
 * @author YinJunBiao
 * @date 2025/2/19 23:32
 * @Description
 */
public class RpcClientFactory {
    static {
        SPILoader.load(RpcClient.class);
    }

    public static RpcClient getRpcClient(String key) {
        return SPILoader.getInstance(RpcClient.class, key);
    }
}

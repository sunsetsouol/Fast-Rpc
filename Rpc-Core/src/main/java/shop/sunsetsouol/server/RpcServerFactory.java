package shop.sunsetsouol.server;

import shop.sunsetsouol.spi.SPILoader;

/**
 * @author YinJunBiao
 * @date 2025/2/19 23:32
 * @Description
 */
public class RpcServerFactory {
    static {
        SPILoader.load(RpcServer.class);
    }

    public static RpcServer getRpcServer(String key) {
        return SPILoader.getInstance(RpcServer.class, key);
    }
}

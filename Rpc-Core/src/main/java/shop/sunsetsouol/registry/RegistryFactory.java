package shop.sunsetsouol.registry;

import shop.sunsetsouol.spi.SPILoader;

/**
 * @author YinJunBiao
 * @date 2025/2/6 10:58
 * @Description
 */
public class RegistryFactory {
    static {
        SPILoader.load(Registry.class);
    }

    public static Registry getRegister(String key){
        return SPILoader.getInstance(Registry.class, key);
    }
}

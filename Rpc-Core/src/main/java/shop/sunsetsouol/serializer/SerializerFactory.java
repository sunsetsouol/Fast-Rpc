package shop.sunsetsouol.serializer;


import shop.sunsetsouol.spi.SPILoader;


public class SerializerFactory {

    static {
        SPILoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JsonSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SPILoader.getInstance(Serializer.class, key);
    }

}

package shop.sunsetsouol.spi;

import cn.hutool.core.io.resource.ResourceUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YinJunBiao
 * @date 2025/2/5 22:52
 * @Description
 */
public class SPILoader {

    /**
     * 系统SPI目录
     */
    private static final String SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    /**
     * 用户自定义SPI目录
     */
    private static final String[] SCAN_DIRS = {SYSTEM_SPI_DIR};

    /**
     * 工厂模式存储已加载的类：接口类 =>（key => 实现类）
     */
    private static Map<Class<?>, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存（避免重复 new），类路径 => 对象实例，单例模式
     */
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    public static void load(Class<?> loadClass){
        for (String scanDir : SCAN_DIRS) {
            String resourceDir = scanDir + loadClass.getName();
            URL resource = ResourceUtil.getResource(resourceDir);
            try{
                InputStream inputStream = resource.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                String[] split = line.split("=");
                if (split.length != 2){
                    throw new RuntimeException("spi配置错误");
                }
                Class<?> imployeeClass = Class.forName(split[1]);
                Map<String , Class<?>> implClass = new HashMap<>();
                implClass.put(split[0], imployeeClass);
                loaderMap.put(loadClass, implClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static<T> T getInstance(Class<T> tClass, String key) {
        Map<String, Class<?>> classMap = loaderMap.get(tClass);
        if (classMap == null){
            throw new RuntimeException("未加载" + tClass.getName());
        }
        if (!classMap.containsKey(key)){
            throw new RuntimeException("不存在" + tClass.getName() + "的" + key);
        }
        Class<?> implClass = classMap.get(key);
        String implClassName = implClass.getName();
        if (!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return (T)instanceCache.get(implClassName);
    }
}

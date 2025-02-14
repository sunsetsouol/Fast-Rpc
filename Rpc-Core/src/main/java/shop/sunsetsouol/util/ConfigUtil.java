package shop.sunsetsouol.util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import shop.sunsetsouol.config.RpcConfig;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @author YinJunBiao
 * @date 2025/2/6 20:37
 * @Description
 */
public class ConfigUtil {
    
    public static RpcConfig readConfig() {
        RpcConfig rpcConfig = null;

        try(InputStream content = ConfigUtil.class.getClassLoader().getResourceAsStream("application.yaml")) {
            Yaml yaml = new Yaml(new Constructor(RpcConfig.class));
            Iterable<Object> its = yaml.loadAll(content);

            Iterator<Object> iterator = its.iterator();
            while(iterator.hasNext()) {
                rpcConfig = (RpcConfig) iterator.next();
            }

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        return rpcConfig;
    }
}

package shop.sunsetsouol.loadbalancer;

import shop.sunsetsouol.spi.SPILoader;

/**
 * @author YinJunBiao
 * @date 2025/2/14 11:36
 * @Description
 */
public class LoadBalancerFactory {
    static {
        SPILoader.load(LoadBalancer.class);
    }

    private static final String defaultLoadBalancer = "random";
    public static LoadBalancer getInstance(String key){
        if (key == null || key.isEmpty()){
            key = defaultLoadBalancer;
        }
        return SPILoader.getInstance(LoadBalancer.class, key);
    }
}

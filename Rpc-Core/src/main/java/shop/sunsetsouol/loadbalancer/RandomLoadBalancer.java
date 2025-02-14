package shop.sunsetsouol.loadbalancer;

import shop.sunsetsouol.model.ServiceMetaData;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author YinJunBiao
 * @date 2025/2/14 12:42
 * @Description
 */
public class RandomLoadBalancer implements LoadBalancer {
    Random random = new Random();
    @Override
    public ServiceMetaData select(Map<String, Object> requestParams, List<ServiceMetaData> serviceMetaData) {
        int size = serviceMetaData.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return serviceMetaData.get(0);
        }
        return serviceMetaData.get(random.nextInt(size));
    }
}

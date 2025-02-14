package shop.sunsetsouol.loadbalancer;

import shop.sunsetsouol.model.ServiceMetaData;

import java.util.List;
import java.util.Map;

/**
 * @author YinJunBiao
 * @date 2025/2/14 11:22
 * @Description
 */
public interface LoadBalancer {

    ServiceMetaData select (Map<String, Object> requestParams, List<ServiceMetaData> serviceMetaData);

}

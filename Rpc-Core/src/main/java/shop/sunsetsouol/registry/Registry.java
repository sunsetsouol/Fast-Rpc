package shop.sunsetsouol.registry;

import shop.sunsetsouol.config.RegistryConfig;
import shop.sunsetsouol.model.ServiceMetaData;

import java.util.List;

public interface Registry {

    void init(RegistryConfig registryConfig);

    void register(ServiceMetaData registryMetaData) ;

    void unRegister(ServiceMetaData registryMetaData);

    List<ServiceMetaData> getServiceAddress(String serviceName);

    void heartbeat();
}

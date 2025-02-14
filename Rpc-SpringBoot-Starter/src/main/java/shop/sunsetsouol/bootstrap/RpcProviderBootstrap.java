package shop.sunsetsouol.bootstrap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import shop.sunsetsouol.RpcApplication;
import shop.sunsetsouol.annotiation.RpcService;
import shop.sunsetsouol.config.RegistryConfig;
import shop.sunsetsouol.config.RpcConfig;
import shop.sunsetsouol.model.ServiceMetaData;
import shop.sunsetsouol.registry.LocalRegistry;
import shop.sunsetsouol.registry.Registry;
import shop.sunsetsouol.registry.RegistryFactory;

/**
 * @author YinJunBiao
 * @date 2025/2/13 11:34
 * @Description
 */
public class RpcProviderBootstrap implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null){
            Class<?> interfalceClass = beanClass.getInterfaces()[0];
            // 本地注册
            String serviceName = interfalceClass.getName();
            LocalRegistry.register(serviceName, beanClass);
            // 注册到注册中心
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry register = RegistryFactory.getRegister(registryConfig.getRegisterType());
            ServiceMetaData serviceMetaData = new ServiceMetaData();
            serviceMetaData.setServiceName(serviceName);
            serviceMetaData.setServiceHost(rpcConfig.getServerHost());
            serviceMetaData.setServicePort(rpcConfig.getServerPort());
            register.register(serviceMetaData);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

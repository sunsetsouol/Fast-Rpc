package shop.sunsetsouol.bootstrap;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import shop.sunsetsouol.RpcApplication;
import shop.sunsetsouol.annotiation.EnableRpc;
import shop.sunsetsouol.config.RpcConfig;
import shop.sunsetsouol.server.tcp.VertxTcpServer;

/**
 * @author YinJunBiao
 * @date 2025/2/6 19:51
 * @Description
 */
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean isProvider = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("isProvider");

        RpcApplication.init();

        //  如果是服务提供者，启动服务器接收请求
        if (isProvider) {
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        }
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
    }
}

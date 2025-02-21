package shop.sunsetsouol.proxy;

import shop.sunsetsouol.RpcApplication;
import shop.sunsetsouol.config.RpcConfig;
import shop.sunsetsouol.loadbalancer.LoadBalancer;
import shop.sunsetsouol.loadbalancer.LoadBalancerFactory;
import shop.sunsetsouol.model.RpcRequest;
import shop.sunsetsouol.model.RpcResponse;
import shop.sunsetsouol.model.ServiceMetaData;
import shop.sunsetsouol.registry.Registry;
import shop.sunsetsouol.registry.RegistryEnum;
import shop.sunsetsouol.registry.RegistryFactory;
import shop.sunsetsouol.retry.RetryStrategy;
import shop.sunsetsouol.retry.RetryStrategyFactory;
import shop.sunsetsouol.server.RpcClient;
import shop.sunsetsouol.server.RpcClientFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YinJunBiao
 * @date 2025/2/11 13:44
 * @Description
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        Registry register = RegistryFactory.getRegister(RegistryEnum.ETCD.getType());
        List<ServiceMetaData> serviceAddress = register.getServiceAddress(serviceName);
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        String loadBalanceKey = rpcConfig.getLoadBalancer();
        LoadBalancer loadbalancer = LoadBalancerFactory.getInstance(loadBalanceKey);
        // 参数名-》参数值
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            map.put(method.getParameters()[i].getName(), args[i]);
        }
        ServiceMetaData host = loadbalancer.select(map, serviceAddress);

        RetryStrategy retryStrategy = RetryStrategyFactory.getRetryStrategy(rpcConfig.getRetry());
        RpcClient rpcClient = RpcClientFactory.getRpcClient(rpcConfig.getClient());

        RpcResponse rpcResponse = retryStrategy.retry(
                () -> rpcClient.doRequest(rpcRequest, host)
        );

        if (rpcResponse != null) {
            return rpcResponse.getData();
        }
        return null;
    }
}

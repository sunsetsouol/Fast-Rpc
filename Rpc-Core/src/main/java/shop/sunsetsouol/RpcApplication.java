package shop.sunsetsouol;

import shop.sunsetsouol.config.RegistryConfig;
import shop.sunsetsouol.config.RpcConfig;
import shop.sunsetsouol.registry.Registry;
import shop.sunsetsouol.registry.RegistryFactory;
import shop.sunsetsouol.util.ConfigUtil;

/**
 * @author YinJunBiao
 * @date 2025/2/5 21:51
 * @Description
 */
public class RpcApplication {

    private volatile static RpcConfig rpcConfig;
    public static void init(){
        rpcConfig = ConfigUtil.readConfig();
        init(rpcConfig);
    }
    public static void init(RpcConfig rpcConfig){
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();

        Registry register = RegistryFactory.getRegister(registryConfig.getRegisterType());

        register.init(registryConfig);
    }

    public static RpcConfig getRpcConfig(){
        if (rpcConfig == null){
            synchronized (RpcApplication.class){
                if (rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }

}

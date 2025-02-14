package shop.sunsetsouol.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YinJunBiao
 * @date 2025/2/5 21:55
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcConfig {

    /**
     * 服务器主机名
     */
    private String serverHost;

    /**
     * 服务器端口号
     */
    private Integer serverPort;

    /**
     * 序列化器
     */
    private String serializer;

    /**
     * 负载均衡器
     */
    private String loadBalancer;


    private RegistryConfig registryConfig ;
}

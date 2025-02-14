package shop.sunsetsouol.model;

import lombok.Data;

/**
 * @author YinJunBiao
 * @date 2025/2/5 22:10
 * @Description
 */
@Data
public class ServiceMetaData {

    /**
     * 服务名
     */
    private String serviceName = "service";

    /**
     * 服务地址
     */
    private String serviceHost = "localhost";

    /**
     * 服务端口
     */
    private int servicePort = 8080;


    public String getServiceAddress() {
        return String.format("%s:%s", serviceHost, servicePort);
    }


}

package shop.sunsetsouol.proxy;

import java.lang.reflect.Proxy;

/**
 * @author YinJunBiao
 * @date 2025/2/13 11:08
 * @Description
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy()
        );
    }

}

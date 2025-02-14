package shop.sunsetsouol.bootstrap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import shop.sunsetsouol.annotiation.RpcReference;
import shop.sunsetsouol.proxy.ServiceProxyFactory;

import java.lang.reflect.Field;

/**
 * @author YinJunBiao
 * @date 2025/2/11 14:14
 * @Description
 */
public class RpcConsumerBootstrap implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference annotation = declaredField.getAnnotation(RpcReference.class);
            if (annotation != null) {
                declaredField.setAccessible(true);
                Object proxy = ServiceProxyFactory.getProxy(declaredField.getType());
                try {
                    declaredField.set(bean, proxy);
                    declaredField.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}

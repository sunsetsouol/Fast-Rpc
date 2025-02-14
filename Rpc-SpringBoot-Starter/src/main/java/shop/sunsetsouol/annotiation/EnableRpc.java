package shop.sunsetsouol.annotiation;

import org.springframework.context.annotation.Import;
import shop.sunsetsouol.bootstrap.RpcConsumerBootstrap;
import shop.sunsetsouol.bootstrap.RpcInitBootstrap;
import shop.sunsetsouol.bootstrap.RpcProviderBootstrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    boolean isProvider() default false;
}

package shop.sunsetsouol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shop.sunsetsouol.annotiation.EnableRpc;

@SpringBootApplication
@EnableRpc
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
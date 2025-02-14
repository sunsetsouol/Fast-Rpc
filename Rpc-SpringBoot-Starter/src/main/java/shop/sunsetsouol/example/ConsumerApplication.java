package shop.sunsetsouol.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shop.sunsetsouol.annotiation.EnableRpc;

/**
 * @author YinJunBiao
 * @date 2025/2/13 13:43
 * @Description
 */

@SpringBootApplication
@EnableRpc
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}


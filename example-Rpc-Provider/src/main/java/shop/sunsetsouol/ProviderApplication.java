package shop.sunsetsouol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shop.sunsetsouol.annotiation.EnableRpc;

@SpringBootApplication
@EnableRpc(isProvider = true)
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
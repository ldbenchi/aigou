package cn.ipanda.aigou;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication  //启动注解
@EnableEurekaClient     //表明客户端
@EnableFeignClients(basePackages = "cn.ipanda.aigou.client")
public class ProductServiceApplication_8002 {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication_8002.class);
    }
}

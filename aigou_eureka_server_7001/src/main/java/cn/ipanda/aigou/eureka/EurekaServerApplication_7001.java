package cn.ipanda.aigou.eureka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@SpringBootApplication
@EnableEurekaServer//eureka的服务端
public class EurekaServerApplication_7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication_7001.class);
    }
}
//  11112222333333

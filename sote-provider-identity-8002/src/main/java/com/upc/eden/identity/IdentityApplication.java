package com.upc.eden.identity;

import com.upc.eden.commens.clients.UserClient;
import com.upc.eden.commens.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = UserClient.class, defaultConfiguration = DefaultFeignConfiguration.class)
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class IdentityApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }

}

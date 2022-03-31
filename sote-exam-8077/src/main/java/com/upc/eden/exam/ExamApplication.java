package com.upc.eden.exam;

import com.upc.eden.commen.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.upc.eden.exam.mapper")
@EnableFeignClients(basePackages = "com.upc.eden.commen.clients",
        defaultConfiguration = DefaultFeignConfiguration.class)
public class ExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

}

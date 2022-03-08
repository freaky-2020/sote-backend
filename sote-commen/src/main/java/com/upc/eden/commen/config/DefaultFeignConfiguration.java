package com.upc.eden.commen.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Author: CS Dong
 * @Date: 2022/02/27/21:36
 * @Description:
 */
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }
}

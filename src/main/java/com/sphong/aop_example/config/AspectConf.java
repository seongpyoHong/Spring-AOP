package com.sphong.aop_example.config;

import com.sphong.aop_example.aspect.Performance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConf {
    @Bean
    public Performance performance() {
        return new Performance();
    }
}

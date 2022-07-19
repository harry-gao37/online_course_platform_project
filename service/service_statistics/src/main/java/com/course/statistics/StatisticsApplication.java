package com.course.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@ComponentScan("com.course")
@MapperScan(basePackages = "com.course.statistics.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.course")
@EnableScheduling
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}

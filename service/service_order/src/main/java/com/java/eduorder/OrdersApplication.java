package com.java.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 曹广福
 * @date 2021/12/12 0:46
 */
@SpringBootApplication
@MapperScan("com.java.eduorder.mapper")
@ComponentScan("com.java")
@EnableDiscoveryClient    //开启服务发现
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class);
    }
}

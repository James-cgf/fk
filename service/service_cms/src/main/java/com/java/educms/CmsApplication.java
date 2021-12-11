package com.java.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 前端模块
 * @date 2021/11/16 10:52
 */

@MapperScan("com.java.educms.mapper")
@ComponentScan(basePackages = "com.java")
@SpringBootApplication
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class);
    }
}

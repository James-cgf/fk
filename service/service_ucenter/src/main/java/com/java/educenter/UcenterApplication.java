package com.java.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 曹广福
 * @date 2021/11/19 0:45
 */

@SpringBootApplication
@ComponentScan("com.java")
@MapperScan("com.java.educenter.mapper")
public class UcenterApplication {

    public static void main(String [] args){
        SpringApplication.run(UcenterApplication.class);
    }

}

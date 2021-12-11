package com.java.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 阿里云oss对象储存（上传图片）
 * @date 2021/11/1 10:07
 */
//因为继承了父pom，所以依赖有mysql，必须要配置mysql数据库信息，这里可以不配置，排除数据库信息
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.java")
public class OssApplication {
    public static void main(String [] args){
        SpringApplication.run(OssApplication.class);
    }

}

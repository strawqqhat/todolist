package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czf
 * @Date 2020/4/8 10:13 下午
 */
@SpringBootApplication
@MapperScan("com.dao")
@RestController
public class App {
    @RequestMapping("/")
    public String hello(){
        return "todolist application is working...";
    }
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}

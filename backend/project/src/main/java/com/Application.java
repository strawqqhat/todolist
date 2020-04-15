package com;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czf
 * @Date 2020/4/8 10:13 下午
 */
@EnableSwagger2Doc
@SpringBootApplication
@MapperScan("com.dao")
@RestController
public class Application {
    @ApiOperation("如果程序正在运行，则打印todolist application is working...")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "todolist application is working...";
    }
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
}

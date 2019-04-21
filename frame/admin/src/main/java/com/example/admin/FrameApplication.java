package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//分模块开发的话要手动扫描service，因为不在一个module里面
//配置了scanBasePackages默认设置就失效了，所以controller也要一起扫描了
@SpringBootApplication(scanBasePackages= {"service","com.example.admin.controller","com.example.admin.config"})
@MapperScan("dao")//扫描dao层
@ServletComponentScan(value = "com.example.admin.config.filter")
public class FrameApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameApplication.class, args);
	}

}

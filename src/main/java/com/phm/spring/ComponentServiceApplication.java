package com.phm.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class ComponentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentServiceApplication.class, args);

    }

}

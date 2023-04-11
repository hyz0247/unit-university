package com.example;

import com.example.commmon.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UnitUniversityApplication {

    public static void main(String[] args) {

        //SpringApplication.run(UnitUniversityApplication.class, args);
        //解决Websocket无法注入问题
        SpringApplication springApplication = new SpringApplication(UnitUniversityApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        WebSocketServer.setApplicationContext(configurableApplicationContext);
    }

}

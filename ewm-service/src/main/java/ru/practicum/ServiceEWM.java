package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceEWM {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServiceEWM.class);
        application.run(ServiceEWM.class,args);
    }
}

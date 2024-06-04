package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StatsService {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StatsService.class);
        application.run(StatsService.class,args);
    }
}

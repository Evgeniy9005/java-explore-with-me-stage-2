package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class StatsService {
    public static void main( String[] args )
    {
        SpringApplication application = new SpringApplication(StatsService.class);
      //  application.setDefaultProperties(Collections.singletonMap("server.port","9090"));
        application.run(StatsService.class,args);
    }
}

package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.client.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ServiceEWM
{
    public static void main( String[] args ) {
        SpringApplication application = new SpringApplication(ServiceEWM.class);
        application.run(ServiceEWM.class,args);
    }
}

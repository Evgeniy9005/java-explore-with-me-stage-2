package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class EWMStatsService {
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(EWMStatsService.class);
        application.run(EWMStatsService.class,args);

       /* ApplicationContext context = SpringApplication.run(EWMStatsService.class,args);
        DefaultDateTime dateTime = context.getBean(DefaultDateTime.class);*/

    }
}

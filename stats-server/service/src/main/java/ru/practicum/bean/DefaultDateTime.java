package ru.practicum.bean;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultDateTime {

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getDate() {
        return LocalDateTime.now().format(format);
    }

}

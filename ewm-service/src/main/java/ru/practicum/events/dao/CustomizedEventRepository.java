package ru.practicum.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ru.practicum.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomizedEventRepository {

    List<Event> searchE(String text,
                       List<Integer> categories,
                       Boolean paid,
                       LocalDateTime rangeStart,
                       LocalDateTime rangeEnd,
                       Pageable pageable);
}

package ru.practicum.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ru.practicum.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CustomizedEventRepository {

    List<Event> searchE(String query,
                        Map<String,Object> param,
                        int from,
                        int size,
                        String sort);
}

package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.events.EventsService;
import ru.practicum.events.dto.EventShortDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceEWMTest {

    @Autowired
    private EventsService eventsService;

    @Test
    void test() {
        List<EventShortDto>  eventShortDtoList = eventsService.getEvents("text",
                1,
                true,
                "rangeStart",
                "rangeEnd",
                false,
                "asc",
                0,
                10,
                null
                );
        assertNull(eventShortDtoList);
    }
}
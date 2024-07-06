package ru.practicum.events;


import org.junit.jupiter.api.Test;
import ru.practicum.events.dto.EventShortDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventsServiceImplTest {

    private EventsService eventsService = new EventsServiceImpl();

    @Test
    void test() {
        assertNotNull(eventsService);
        List<EventShortDto> eventShortDtoList = eventsService.getEvents("text",
                1,
                true,
                "rangeStart",
                "rangeEnd",
                false,
                "asc",
                0,
                10
        );
        assertNull(eventShortDtoList);
    }
}
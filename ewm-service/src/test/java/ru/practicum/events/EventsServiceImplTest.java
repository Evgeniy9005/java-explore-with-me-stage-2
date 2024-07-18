package ru.practicum.events;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.constants.SortEvents;
import ru.practicum.events.dto.EventShortDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class EventsServiceImplTest {

    private final EventsService eventsService;

    @Test
    void test() {
        assertNotNull(eventsService);
        List<EventShortDto> eventShortDtoList = eventsService.getEvents("text",
                List.of(1),
                true,
                "rangeStart",
                "rangeEnd",
                false,
                SortEvents.EVENT_DATE,
                0,
                10,
                null
        );
        assertNull(eventShortDtoList);
    }
}
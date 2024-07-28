package ru.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.constants.SortEvents;
import ru.practicum.events.EventsService;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.users.UserService;
import ru.practicum.users.request.model.ParticipationRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceEWMTest {

    @Autowired
    private UserService userService;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private EntityManager entityManager;


    @BeforeAll
    static void start() {

    }

    @AfterEach
    void end() {
     //   entityManager.createNativeQuery("ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1").executeUpdate();
     //   entityManager.createNativeQuery("ALTER TABLE ITEM_REQUESTS ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }

    @Test
    void test() {
        List<EventShortDto>  eventShortDtoList = eventsService.getEvents("text",
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
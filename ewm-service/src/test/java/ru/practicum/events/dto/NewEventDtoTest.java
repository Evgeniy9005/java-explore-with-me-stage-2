package ru.practicum.events.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.data.Data;
import ru.practicum.events.model.Location;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewEventDtoTest {
    private static List<NewEventDto> newEventDtoList;

    @BeforeAll
    static void start() {
        newEventDtoList = Data.generationData(1,NewEventDto.class);
        Data.printList(newEventDtoList);
    }

    @Test
    void isPaid() {
        assertTrue(newEventDtoList.get(0).isPaid());
    }

    @Test
    void isRequestModeration() {
        assertFalse(newEventDtoList.get(0).isRequestModeration());
    }

    @Test
    void getAnnotation() {
        assertEquals("Краткое описание 1",newEventDtoList.get(0).getAnnotation());
    }

    @Test
    void getCategory() {
        assertEquals(1,newEventDtoList.get(0).getCategory());
    }

    @Test
    void getDescription() {
        assertEquals("Полное описание события 1",newEventDtoList.get(0).getDescription());
    }

    @Test
    void getEventDate() {
        assertNotNull(newEventDtoList.get(0).getEventDate());
    }

    @Test
    void getLocation() {
        assertEquals(new Location(52.2f,54.4f),newEventDtoList.get(0).getLocation());
    }

    @Test
    void getParticipantLimit() {
        assertEquals(0,newEventDtoList.get(0).getParticipantLimit());
    }

    @Test
    void getTitle() {
        assertEquals("Заголовок 1",newEventDtoList.get(0).getTitle());
    }

}
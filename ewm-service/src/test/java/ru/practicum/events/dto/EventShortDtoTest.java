package ru.practicum.events.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.data.Data;
import ru.practicum.users.dto.UserShortDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventShortDtoTest {

    private static List<EventShortDto> eventShortDtoList;

    @BeforeAll
    static void start() {
        eventShortDtoList = Data.generationData(1,EventShortDto.class);
        Data.printList(eventShortDtoList);
    }

    @Test
    void getAnnotation() {
        assertEquals("Краткое описание 1",eventShortDtoList.get(0).getAnnotation());
    }

    @Test
    void getCategory() {
        assertEquals(new CategoryDto(1,"Категория 1"),eventShortDtoList.get(0).getCategory());
    }

    @Test
    void getConfirmedRequests() {
        assertEquals(2,eventShortDtoList.get(0).getConfirmedRequests());
    }

    @Test
    void getEventDate() {
        assertNotNull(eventShortDtoList.get(0).getConfirmedRequests());
    }

    @Test
    void getId() {
        assertEquals(1,eventShortDtoList.get(0).getId());
    }

    @Test
    void getInitiator() {
        assertEquals(new UserShortDto(1,"Пользователь1"),eventShortDtoList.get(0).getInitiator());
    }

    @Test
    void getPaid() {
        assertTrue(eventShortDtoList.get(0).getPaid());
    }

    @Test
    void getTitle() {
        assertEquals("Заголовок 1",eventShortDtoList.get(0).getTitle());
    }

    @Test
    void getViews() {
        assertEquals(10,eventShortDtoList.get(0).getViews());
    }
}
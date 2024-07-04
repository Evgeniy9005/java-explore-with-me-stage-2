package ru.practicum.events.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.data.Data;
import ru.practicum.events.constants.State;
import ru.practicum.events.model.Location;
import ru.practicum.users.dto.UserShortDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventFullDtoTest {

    private static List<EventFullDto> eventFullDtoList;

    @BeforeAll
    static void  start() {
    eventFullDtoList = Data.generationData(3,EventFullDto.class);
    Data.printList(eventFullDtoList);
    }

    @Test
    void getAnnotation() {
        assertEquals("Краткое описание 1",eventFullDtoList.get(0).getAnnotation());
    }

    @Test
    void getCategory() {
        assertEquals(new CategoryDto(1,"Категория 1"),eventFullDtoList.get(0).getCategory());
    }

    @Test
    void getConfirmedRequests() {
        assertEquals(2,eventFullDtoList.get(0).getConfirmedRequests());
    }

    @Test
    void getCreatedOn() {
        assertNotNull(eventFullDtoList.get(0).getCreatedOn());
    }

    @Test
    void getDescription() {
        assertEquals("Полное описание события 1",eventFullDtoList.get(0).getDescription());
    }

    @Test
    void getEventDate() {
        assertNotNull(eventFullDtoList.get(0).getEventDate());
    }

    @Test
    void getId() {
        assertEquals(1,eventFullDtoList.get(0).getId());
    }

    @Test
    void getInitiator() {
        assertEquals(new UserShortDto(1,"Пользователь1"),eventFullDtoList.get(0).getInitiator());
    }

    @Test
    void getLocation() {
        assertEquals(new Location(52.2f,54.4f),eventFullDtoList.get(0).getLocation());
    }

    @Test
    void getPaid() {
        assertTrue(eventFullDtoList.get(0).getPaid());
    }

    @Test
    void getParticipantLimit() {
        assertEquals(0,eventFullDtoList.get(0).getParticipantLimit());
    }

    @Test
    void getPublishedOn() {
        assertNotNull(eventFullDtoList.get(0).getPublishedOn());
    }

    @Test
    void getRequestModeration() {
        assertFalse(eventFullDtoList.get(0).getRequestModeration());
    }

    @Test
    void getState() {
        assertEquals(State.PUBLISHED,eventFullDtoList.get(0).getState());
    }

    @Test
    void getTitle() {
        assertEquals("Заголовок 1",eventFullDtoList.get(0).getTitle());
    }

    @Test
    void getViews() {
        assertEquals(10,eventFullDtoList.get(0).getViews());
    }
}
package ru.practicum.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.events.dto.NewEventDto;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventsController.class)
class EventsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventsService eventsService;

    @Autowired
    private MockMvc mvc;

    private NewEventDto newEventDto;

    @Test
    void getEvents() {

    }

    @Test
    void getEvent() {
    }

    @Test
    void addEventUser() {
        mvc.perform(post("/users/1/events")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void getEventsAddedCurrentUser() {
    }

    @Test
    void updateEventAndStatus() {
    }
}
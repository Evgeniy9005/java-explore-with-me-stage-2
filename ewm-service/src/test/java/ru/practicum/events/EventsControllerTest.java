package ru.practicum.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.client.StatsClient;
import ru.practicum.data.Data;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Location;
import ru.practicum.stats.Stats;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventsController.class)
class EventsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventsService eventsService;

    @Mock
    private StatsClient statsClient;

    @Autowired
    private MockMvc mvc;

    private NewEventDto newEventDto;

    private List<EventShortDto> eventShortDtoList;

    @BeforeEach
    void start() {
    newEventDto = NewEventDto.builder()
            .annotation("Краткое описание события")
            .paid(false)
            .location(new Location(53.1F,54.2F))
            .eventDate("2024-12-31 15:10:05")
            .description("Полное описание события")
            .participantLimit(0)
            .requestModeration(true)
            .title("Событие")
            .build();
    eventShortDtoList = Data.generationData(2,EventShortDto.class);
    Data.printList(eventShortDtoList,">>>");
    }

    @Test
    void getEvents() {
        try (MockedStatic<Stats> theMock = Mockito.mockStatic(Stats.class)) {
            theMock.when(Stats::getStatsClient).thenReturn(statsClient);

            when(statsClient.put(any())).thenReturn("Ответ");
            mvc.perform(post("/events")
                            .with(request -> {
                                request.setRemoteAddr("192.168.0.1");
                                return request;
                            })
                            .content(objectMapper.writeValueAsString(eventShortDtoList))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .param("text","какой-то текст")
                            .param("categories","1")
                            .param("paid","true")
                            .param("rangeStart","2024-11-30 15:10:05")
                            .param("rangeEnd","2024-12-31 15:10:05")
                            .param("onlyAvailable","true")
                            .param("sort","desc"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(statsClient).get(any(),any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void getEvent() {


        try (MockedStatic<Stats> theMock = Mockito.mockStatic(Stats.class)) {
            theMock.when(Stats::getStatsClient).thenReturn(statsClient);

            when(statsClient.put(any())).thenReturn("Ответ");
            mvc.perform(post("/events/1")
                            .with(request -> {
                                request.setRemoteAddr("192.168.0.1");
                                return request;
                            })
                            .content(objectMapper.writeValueAsString(eventShortDtoList))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(statsClient).get(any(),any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void addEventUser() {
       // HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        try (MockedStatic<Stats> theMock = Mockito.mockStatic(Stats.class)) {
            theMock.when(Stats::getStatsClient).thenReturn(statsClient);

            when(statsClient.put(any())).thenReturn("Ответ");
            mvc.perform(post("/users/1/events")
                            .with(request -> {
                                request.setRemoteAddr("192.168.0.1");
                                return request;
                            })
                            .content(objectMapper.writeValueAsString(newEventDto))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    void getEventsAddedCurrentUser() {
    }

    @Test
    void updateEventAndStatus() {
    }
}
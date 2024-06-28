package ru.practicum.stats;


import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EndpointHitDto;
import dto.ViewStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = StatsController.class)
class StatsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatsService statsService;

    @Autowired
    private MockMvc mvc;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    EndpointHitDto endpointHitDto;

    private LocalDateTime time;
    private String formatTime;

    private ViewStats viewStats1;
    private ViewStats viewStats2;
    private ViewStats viewStats3;

    private List<ViewStats> viewStatsList;


    @BeforeEach
    void start() {

        time = LocalDateTime.now();

        formatTime = time.format(formatter);

         endpointHitDto = EndpointHitDto.builder()
                .app("app")
                .uri("/uri")
                .ip("198.168.0.1")
                .timestamp(formatTime)
                .build();

        viewStats1 = new ViewStats("app1","/uri1",1);
        viewStats2 = new ViewStats("app2","/uri2",2);
        viewStats3 = new ViewStats("app3","/uri3",3);

        viewStatsList = List.of(viewStats1,viewStats2,viewStats3);


    }

    @Test
    void addStats() throws Exception {

        mvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getStats() throws Exception {

        mvc.perform(get("/stats")
                        .content(objectMapper.writeValueAsString(viewStatsList))
                        .param("start","2020-05-05 00:00:00")
                        .param("end","2035-05-05 00:00:00")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
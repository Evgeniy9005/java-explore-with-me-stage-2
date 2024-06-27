package ru.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EndpointHitDto;
import dto.ViewStats;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.data.Data;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;
import ru.practicum.stats.StatsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.data.Data.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private EndpointHitMapper hitMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate template;

    private static List<ViewStats> viewStatsList;

    private static List<EndpointHit> endpointHitList;

    private List<EndpointHitDto> endpointHitDtoList;

    @BeforeAll
    static void data() {
        viewStatsList = generationData(5,ViewStats.class);
        endpointHitList = generationData(5,EndpointHit.class);
        printList(endpointHitList);

    }

    @BeforeEach
    void start() {
        endpointHitDtoList = endpointHitList.stream()
                .map(endpointHit -> hitMapper.toEndpointHitDto(endpointHit))
                .collect(Collectors.toList());
        printList(endpointHitDtoList);
    }

    @Test
    void methodTest() {
        EndpointHitDto endpointHitDto = statsService.addStats(endpointHitDtoList.get(0));
        assertEquals(endpointHitDto,endpointHitDtoList.get(0));
    }

    @Test
    void methodTestGetStats() {
        List<EndpointHitDto> uris = endpointHitDtoList.stream()
                .map(endpointHit -> statsService.addStats(endpointHit.toBuilder()
                        .app("app")
                        .uri("uri/1")
                        .build()))
                .collect(Collectors.toList());
        String start = LocalDateTime.now().minusDays(1).format(getFormatter());
        String end = LocalDateTime.now().plusDays(1).format(getFormatter());


        List<ViewStats> viewStats = statsService.getStats(start,end,"uri/1",false);
        assertEquals(1,viewStats.size());
        assertEquals(5,viewStats.get(0).getHits());
        //viewStats.stream().forEach(System.out::println);

        uris = endpointHitDtoList.stream().limit(4)
                .map(endpointHit -> statsService.addStats(endpointHit.toBuilder()
                        .app("app")
                        .uri("uri/2")
                        .build()))
                .collect(Collectors.toList());
        viewStats = statsService.getStats(start,end,"uri/2",false);
        assertEquals(1,viewStats.size());
        assertEquals(4,viewStats.get(0).getHits());

        viewStats = statsService.getStats(start,end,"uri/1,uri/2",false);
        assertEquals(2,viewStats.size());
        assertEquals(5,viewStats.get(0).getHits());
        assertEquals(4,viewStats.get(1).getHits());

        uris = endpointHitDtoList.stream()
                .limit(3)
                .map(endpointHit -> statsService.addStats(endpointHit.toBuilder()
                        .app("app")
                        .uri("uri/3")
                        .ip("198.168.1.1")
                        .build()))
                .collect(Collectors.toList());
        viewStats = statsService.getStats(start,end,"uri/3",true);
        viewStats.stream().forEach(System.out::println);
    }

}
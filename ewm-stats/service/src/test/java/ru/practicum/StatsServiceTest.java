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
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
        viewStatsList = Data.generationData(5,ViewStats.class);
        endpointHitList = Data.generationData(5,EndpointHit.class);
        Data.printList(endpointHitList);

    }

    @BeforeEach
    void start() {
        endpointHitDtoList = endpointHitList.stream()
                .map(endpointHit -> hitMapper.toEndpointHitDto(endpointHit))
                .collect(Collectors.toList());
        Data.printList(endpointHitDtoList);
    }

    @Test
    void methodTest() {
        EndpointHitDto endpointHitDto = statsService.addStats(endpointHitDtoList.get(0));
        System.out.println(endpointHitDto);
        //assertEquals();
    }

}
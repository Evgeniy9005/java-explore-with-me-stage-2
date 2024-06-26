package ru.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ViewStats;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;

import java.util.List;

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

    private List<ViewStats> viewStatsList;

    private List<EndpointHit> endpointHitList;

    @BeforeAll
    static void data() {

    }

    @BeforeEach
    void start() {

    }

}
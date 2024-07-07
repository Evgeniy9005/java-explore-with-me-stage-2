package ru.practicum.stats;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
class StatsTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    void getStatsClient() {
        assertNotNull(Stats.getStatsClient());
    }

   /* @Test
    void hit() {

        Stats.hit("app", httpServletRequest);
    }*/
}
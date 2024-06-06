package ru.practicum.stats;

import dto.EndpointHitDto;
import dto.ViewStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dao.StatsRepository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;
import ru.practicum.model.EndpointHitMapperImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

    private EndpointHitMapper endpointHitMapper =  new EndpointHitMapperImpl();

    @Mock
    private StatsRepository statsRepository;

    private StatsService statsService;
    private EndpointHitDto endpointHitDto;
    private EndpointHit endpointHit;

    private ViewStats viewStats1;
    private ViewStats viewStats2;
    private ViewStats viewStats3;

    private List<ViewStats> viewStatsList;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LocalDateTime time;
    private String formatTime;

    private LocalDateTime start;

    private String formatStart;

    private LocalDateTime end;

    private String formatEnd;

    @BeforeEach
    void start() {
        statsService = new StatsServiceImpl(statsRepository,endpointHitMapper);

        time = LocalDateTime.now();

        formatTime = time.format(formatter);

        start = time.minusMonths(1);

        formatStart = start.format(formatter);

        end = time.plusMonths(1);

        formatEnd = end.format(formatter);

        endpointHit = EndpointHit.builder()
                .app("app")
                .uri("/uri")
                .ip("198.168.0.1")
                .timestamp(time)
                .build();

        endpointHitDto = EndpointHitDto.builder()
                .app("app")
                .uri("/uri")
                .ip("198.168.0.1")
                .timestamp(formatTime)
                .build();

        viewStats1 = new ViewStats("app1","/uri1",1L);
        viewStats2 = new ViewStats("app2","/uri2",2L);
        viewStats3 = new ViewStats("app3","/uri3",3L);

        viewStatsList = List.of(viewStats1,viewStats2,viewStats3);
    }

    @Test
    void addStats() {
        when(statsRepository.save(any(EndpointHit.class))).thenReturn(endpointHit);
        assertEquals(endpointHitDto,statsService.addStats(endpointHitDto));
        verify(statsRepository).save(any(EndpointHit.class));
    }

    @Test
    void getStats() {
        viewStatsList = List.of(viewStats1,viewStats2,viewStats3);

        when(statsRepository.getCountHit(LocalDateTime.parse(formatStart,formatter),
                LocalDateTime.parse(formatEnd,formatter))).thenReturn(viewStatsList);
        statsService.getStats(formatStart,formatEnd,null,false);
        verify(statsRepository).getCountHit(LocalDateTime.parse(formatStart,formatter),
                LocalDateTime.parse(formatEnd,formatter));

    }
}
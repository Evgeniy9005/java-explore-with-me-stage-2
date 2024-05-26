package ru.practicum.dao;
import dto.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit,Integer> {

       @Query("select new dto.ViewStats(e.app, e.uri, count(e.uri)) " +
               "from EndpointHit as e " +
               "group by e.uri")
        List<ViewStats> getCountHit();
}

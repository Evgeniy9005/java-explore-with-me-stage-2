package ru.practicum.dao;

import dto.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.EndpointHit;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit,Integer> {

       @Query("select new dto.ViewStats(e.app, e.uri, count(e.uri)) " +
               "from EndpointHit as e " +
               "where e.timestamp >= ?1 and e.timestamp <= ?2 " +
               "group by e.uri, e.app " +
               "order by count(e.uri) desc")
        List<ViewStats> getCountHit(LocalDateTime start, LocalDateTime end);

    @Query("select new dto.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 " +
            "group by e.uri, e.app")
    List<ViewStats> getCountHitUnique(LocalDateTime start, LocalDateTime end);

    @Query("select new dto.ViewStats(e.app, e.uri, count(e.uri)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= :start and e.timestamp <= :end and e.uri in(:uris) " +
            "group by e.uri, e.app " +
            "order by count(e.uri) desc")
    List<ViewStats> getCountHit(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("uris") List<String> uris);

    @Query("select new dto.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= :start and e.timestamp <= :end and e.uri in(:uris) " +
            "group by e.uri, e.app")
    List<ViewStats> getCountHitUnique(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") List<String> uris);

}

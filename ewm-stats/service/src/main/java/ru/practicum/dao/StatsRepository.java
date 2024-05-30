package ru.practicum.dao;

import dto.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.EndpointHit;
import java.time.LocalDateTime;
import java.util.List;
import java.lang.Integer;

public interface StatsRepository extends JpaRepository<EndpointHit,Integer> {

       @Query("select new dto.ViewStats(e.app, e.uri, count(e.uri)) " +
               "from EndpointHit as e " +
               "where e.timestamp >= ?1 and e.timestamp <= ?2 " +
               "group by e.uri")
        List<ViewStats> getCountHit(LocalDateTime start, LocalDateTime end);

    @Query("select new dto.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 " +
            "group by e.uri")
    List<ViewStats> getCountHitUnique(LocalDateTime start, LocalDateTime end);

    @Query("select new dto.ViewStats(e.app, e.uri, count(e.uri)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= :start and e.timestamp <= :end and e.id in(:uris) " +
            "group by e.uri")
    List<ViewStats> getCountHit(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("uris") List<Integer> uris);

    @Query("select new dto.ViewStats(e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp >= :start and e.timestamp <= :end and e.id in(:uris) " +
            "group by e.uri")
    List<ViewStats> getCountHitUnique(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") List<Integer> uris);

    List<EndpointHit> findDistinctByTimestampBetween(LocalDateTime start, LocalDateTime end);

}

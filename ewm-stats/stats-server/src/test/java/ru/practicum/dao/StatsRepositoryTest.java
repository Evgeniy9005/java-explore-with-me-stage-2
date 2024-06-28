package ru.practicum.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;


@DataJpaTest
class StatsRepositoryTest {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void start() {

    }

    @AfterEach
    void end() {
        entityManager.createNativeQuery("ALTER TABLE STATS ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }

    @Test
    void getCountHit() {
    }

    @Test
    void getCountHitUnique() {
    }

    @Test
    void testGetCountHit() {
    }

    @Test
    void testGetCountHitUnique() {
    }
}
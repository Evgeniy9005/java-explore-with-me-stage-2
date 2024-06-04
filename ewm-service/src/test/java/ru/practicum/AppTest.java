package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

public class AppTest {

@Test
    void test() {
    URI uri = UriComponentsBuilder
            .fromUriString("https://example.com/hotels/{hotel}?q={q}")
            .build("Westin", "123");
    System.out.println(uri);
}
}

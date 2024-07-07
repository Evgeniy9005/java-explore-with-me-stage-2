package ru.practicum.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.client.StatsClient;
import ru.practicum.stats.Stats;
import ru.practicum.users.dto.UserDto;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsersControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private StatsClient statsClient;

    @Autowired
    private MockMvc mvc;

    @Test
    void addNewUser() {
        try (MockedStatic<Stats> theMock = Mockito.mockStatic(Stats.class)) {
            theMock.when(Stats::getStatsClient).thenReturn(statsClient);

            when(statsClient.put(any())).thenReturn("Ответ");
            mvc.perform(get("/admin/users")
                            .with(request -> {
                                request.setRemoteAddr("192.168.0.1");
                                return request;
                            })
                            .content(objectMapper.writeValueAsString(new UserDto("e",1,"name")))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(statsClient).get(any(),any());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
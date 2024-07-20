package ru.practicum.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.compilations.dto.CompilationDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl {

    private static final String COMPILATION = "Compilation: ";

    private static final String APP = "ewm-main-service";


    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam Boolean pinned, //искать только закрепленные/не закрепленные подборки
                                                @RequestParam (defaultValue = "0") int from,
                                                @RequestParam (defaultValue = "10") int size,
                                                HttpServletRequest request
    ) {
        log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable @Positive int compId, HttpServletRequest request) {
        log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        return null;
    }
}

package ru.practicum.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class CompilationController {

    private static final String COMPILATION = "Compilation: ";

    private static final String APP = "ewm-main-service";

    @GetMapping()
    public List<CompilationDto> getCompilations(@RequestParam Boolean pinned, //искать только закрепленные/не закрепленные подборки
                                                @RequestParam (defaultValue = "0") int from,
                                                @RequestParam (defaultValue = "10") int size,
                                                HttpServletRequest request
    ) {
        log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId, HttpServletRequest request) {
        log.info("{} отправлена статистика {}",COMPILATION,getStatsClient().put(hit(APP,request)));
        return null;
    }
}

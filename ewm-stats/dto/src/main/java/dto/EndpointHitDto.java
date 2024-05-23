package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Data
public class EndpointHitDto {
    private final String app;
    private final String uri;
    private final String ip;
    private final String timestamp;
}

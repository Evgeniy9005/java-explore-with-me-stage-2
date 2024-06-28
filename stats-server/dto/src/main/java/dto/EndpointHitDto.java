package dto;

import lombok.*;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}

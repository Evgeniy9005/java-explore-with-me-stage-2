package dto;

import lombok.Builder;

@Builder
public class ViewStats {
    private final String app;
    private final String uri;
    private final int hits;
}

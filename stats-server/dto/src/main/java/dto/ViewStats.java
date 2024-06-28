package dto;

import lombok.*;
import java.util.Objects;

@Setter
@Getter
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits.intValue();
    }

    public ViewStats() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewStats viewStats = (ViewStats) o;
        return hits == viewStats.hits && Objects.equals(app, viewStats.app) && Objects.equals(uri, viewStats.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, hits);
    }
}

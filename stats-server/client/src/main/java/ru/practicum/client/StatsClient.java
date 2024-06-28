package ru.practicum.client;

import ru.practicum.exceptions.RequestErrorException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatsClient {
    private String host;
    private String port;
    private String schema = "http://";
    private HttpClient client;
    private String urlHit = "/hit";
    private String urlStats = "/stats";
    private String urlStart = "start";
    private String urlEnd = "end";
    private String urlUris = "uris";
    private String urlUnique = "unique";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String timeStartEncoding;
    private String timeEndEncoding;

    public StatsClient(String urlServer, String port) {
        this.host = urlServer;
        this.port = port;
        this.client = HttpClient.newHttpClient();
    }

    private HttpResponse<String> request(URI url)
            throws IOException, InterruptedException {
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.newHttpClient()
                .send(request1, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RequestErrorException(
                    "Неудачная попытка выполнения запроса данных на сервере! Код ответа: "
                            + response.statusCode()
            );
        }

        return response;
    }

    public String put(String json) {
        String body;
        try {
            URI url = URI.create(schema + host + ":" + port + urlHit);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
           body = response.body();
            if (response.statusCode() != 201) {
                throw new RequestErrorException(
                        "Неудачная попытка выполнения запроса при сохранении данных на сервере! Код ответа: "
                                + response.statusCode()
                );
            }

        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при формировании запроса сохранения данных на сервере! " + e);
        }
        return body;
    }

    public String get(LocalDateTime start, LocalDateTime end) {
        String body;

        try {
            timeStartEncoding = encodeValue(start.format(formatter));
            timeEndEncoding = encodeValue(end.format(formatter));

            URI url = URI.create(schema +
                    host + ":" +
                    port +
                    urlStats + "?" +
                    urlStart + "=" +
                    timeStartEncoding + "&" +
                    urlEnd + "=" +
                    timeEndEncoding);

            body = request(url).body();

        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при формировании запроса данных с сервера! " + e);
        }
        return body;
    }

    public String get(LocalDateTime start, LocalDateTime end, Boolean unique) {
        String body;

        try {
            timeStartEncoding = encodeValue(start.format(formatter));
            timeEndEncoding = encodeValue(end.format(formatter));

            URI url = URI.create(schema +
                    host + ":" +
                    port +
                    urlStats + "?" +
                    urlStart + "=" +
                    timeStartEncoding + "&" +
                    urlEnd + "=" +
                    timeEndEncoding + "&" +
                    urlUnique + "=" +
                    unique.toString());

            body = request(url).body();

        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при формировании запроса данных с сервера! " + e);
        }
        return body;
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

}

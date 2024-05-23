package ru.practicum.client;

import ru.practicum.exceptions.RequestErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    private String urlServer;
    private java.net.http.HttpClient client;

    private String urlHit = "/hit";
    private String urlStats = "/stats";

    HttpClient(String urlServer){
        this.urlServer = urlServer;
        this.client = java.net.http.HttpClient.newHttpClient();
    }

    private HttpResponse<String> request(URI url)
            throws IOException, InterruptedException {
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = java.net.http.HttpClient.newHttpClient()
                .send(request1, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            throw new RequestErrorException(
                    "Неудачная попытка выполнения запроса данных на сервере! Код ответа: "
                            + response.statusCode()
            );
        }

        return response;
    }

   /* public void register() {
        try {
            URI url = URI.create(urlServer+urlReg);
            HttpResponse<String> responseReg = request(url);
            String apiToken = responseReg.body();
            if(!apiToken.isEmpty()) {
                this.apiToken = apiToken;
            }
        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при регистрации на сервере! "+ e);
        }
    }*/

    //должен сохранять состояние менеджера задач через запрос POST /save/<ключ>?API_TOKEN=.
    //должен сохранить POST /hit в базу данных запрос
    public void put(String json) {
        try {
            URI url = URI.create(urlServer+urlHit);
            HttpRequest request = HttpRequest.newBuilder() //Для добавления задачи и обновления задачи
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = java.net.http.HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                throw new RequestErrorException(
                        "Неудачная попытка выполнения запроса при сохранении данных на сервере! Код ответа: "
                                + response.statusCode()
                );
            }

        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при формировании запроса сохранения данных на сервере! "+e);
        }
    }

   /* //должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_TOKEN=
    public String load(String key) {
        String body;
        try {
            URI url = URI.create(urlServer+urlLoad+key+urlApiToken+apiToken);
            body = request(url).body();
        } catch (IOException | InterruptedException e) {
            throw new RequestErrorException("Ошибка при формировании запроса данных с сервера! "+e);
        }
        return body;
    }*/
}

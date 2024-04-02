package org.example;

import lombok.SneakyThrows;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractService {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @SneakyThrows
    protected HttpResponse<String> performHttpCall(HttpRequest request) {
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.Code: " + response.statusCode());
        System.out.println("response.Body: " + response.body());
        return response;
    }
}

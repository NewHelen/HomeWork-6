package org.example;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractService {

    private static final Logger logger = LogManager.getLogger(AbstractService.class);

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @SneakyThrows
    protected HttpResponse<String> performHttpCall(HttpRequest request) {
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("response.Code: " + response.statusCode());
        logger.info("response.Body: " + response.body());
        return response;
    }
}

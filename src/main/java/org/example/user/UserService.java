package org.example.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.AbstractService;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService extends AbstractService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public HttpResponse<String> getUsers() {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL))
                        .GET()
                        .build());
    }

    public HttpResponse<String> getUser(int id) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id))
                        .GET()
                        .build());
    }

    public HttpResponse<String> getUser(String userName) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "?username=" + userName))
                        .GET()
                        .build());
    }

    @SneakyThrows
    public UserDto createUser(UserDto userDto) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL))
                        .header("Content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(userDto)))
                        .build());
       return objectMapper.readValue(response.body(), UserDto.class);
    }

    @SneakyThrows
    public HttpResponse<String> updateUser(int id, UserDto userDto) {
        String updateUserRequest = objectMapper.writeValueAsString(userDto);

        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id))
                        .header("Content-type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(updateUserRequest))
                        .build());
    }

    public HttpResponse<String> deleteUser(int id) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id ))
                        .DELETE()
                        .build());
    }
}

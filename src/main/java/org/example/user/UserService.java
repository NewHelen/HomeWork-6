package org.example.user;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.AbstractService;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService extends AbstractService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson gson = new Gson();

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
    public User createUser(User user) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL))
                        .header("Content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                        .build());
       return gson.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public HttpResponse<String> updateUser(int id, User user) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id))
                        .header("Content-type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                        .build());
    }

    public HttpResponse<String> deleteUser(int id) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id ))
                        .DELETE()
                        .build());
    }
}

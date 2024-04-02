package org.example.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.example.AbstractService;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserService extends AbstractService {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson gson = new Gson();

    public List<User> getUsers() {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL))
                        .GET()
                        .build());
        return gson.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public User getUser(int id) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id))
                        .GET()
                        .build());
        return gson.fromJson(response.body(),User.class);
    }

    public User getUser(String username) {
        List<User> users = getUsers();
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().get();
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
    public User updateUser(int id, User user) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id))
                        .header("Content-type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                        .build());
        return gson.fromJson(response.body(), User.class);
    }

    public int deleteUser(int id) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/" + id ))
                        .DELETE()
                        .build()).statusCode();
    }
}

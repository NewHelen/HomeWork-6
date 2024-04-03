package org.example.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.dto.Post;
import org.example.core.dto.Task;
import org.example.core.dto.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UserService extends AbstractService {

    private static final Logger logger = LogManager.getLogger(AbstractService.class);
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final Gson GSON = new Gson();

    //завдання 1
    public List<User> getUsers() {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users"))
                        .GET()
                        .build());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public User getUser(int id) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + id))
                        .GET()
                        .build());
        return GSON.fromJson(response.body(), User.class);
    }

    public List<User> getUsers(String username) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users?username=" + username))
                        .GET()
                        .build());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    @SneakyThrows
    public User createUser(User user) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users"))
                        .header("Content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(user)))
                        .build());
        return GSON.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public User updateUser(int id, User user) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + id))
                        .header("Content-type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(user)))
                        .build());
        return GSON.fromJson(response.body(), User.class);
    }

    public int deleteUser(int id) {
        return performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + id))
                        .DELETE()
                        .build()).statusCode();
    }

    //завдання 2
    public void getCommentsForUserPost(int userId, String directory) {
        List<Post> posts = getUserPosts(userId);
        int maxId = findMaxPostId(posts);
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/posts/" + maxId + "/comments"))
                        .GET()
                        .build());
        try {
            Files.write(Paths.get(directory + "user-" + userId + "-post-" + maxId + "-comments.json"), response.body().getBytes());
        } catch (IOException e) {
            logger.error("An error occurred: " + e);
        }
    }

    private List<Post> getUserPosts(int userId) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + userId + "/posts"))
                        .GET()
                        .build());
        return GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
    }

    private int findMaxPostId(List<Post> posts) {
        return posts.stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0); // Повернути 0, якщо список постів порожній
    }

    //завдання 3
    public List<Task> getOpenUserTasks(int userId) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + userId + "/todos"))
                        .GET()
                        .build());
        List<Task> taskList = GSON.fromJson(response.body(), new TypeToken<List<Task>>() {
        }.getType());
        return taskList.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }
}

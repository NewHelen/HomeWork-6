package org.example.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.example.AbstractService;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Service extends AbstractService {

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

    public User getUser(String username) {
        List<User> users = getUsers();
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().get();
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
    public List<Comment> getCommentsForUserPost(int userId) {
        List<Post> posts = getUserPosts(userId);
        int maxId = findMaxPostId(posts);
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/posts/" + maxId + "/comments"))
                        .GET()
                        .build());
        List<Comment> commentList = GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
        writeToJsonFile(commentList, "files/user-" + userId + "-post-" + maxId + "-comments.json");
        return commentList;
    }

    private List<Post> getUserPosts(int userId) {
        HttpResponse<String> response = performHttpCall(
                HttpRequest.newBuilder(URI.create(BASE_URL + "/users/" + userId + "/posts"))
                        .GET()
                        .build());
        return GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
    }

    private static int findMaxPostId(List<Post> posts) {
        return posts.stream()
                .mapToInt(Post::getId)
                .max()
                .orElse(0); // Повернути 0, якщо список постів порожній
    }

    private static void writeToJsonFile(List<Comment> commentList, String jsonFileName) {
        try (FileWriter fileWriter = new FileWriter(jsonFileName)) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(fileWriter, commentList);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

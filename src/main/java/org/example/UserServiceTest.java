package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.*;
import java.net.http.HttpResponse;
import java.util.List;

public class UserServiceTest {

    private static final UserService userService = new UserService();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUsersSuccess (){
        int expectedUsersCount =10;
        List<User> users = userService.getUsers();
        Assert.assertEquals(expectedUsersCount, users.size());
    }

    @Test
    public void testGetUserByIdSuccess(){
        User user = userService.getUser(1);
        Assert.assertEquals(1,user.getId());
    }

    @Test
    public void testGetUserByUserNameSuccess(){
        String username = "Antonette";
        User user = userService.getUser(username);
        Assert.assertEquals(username, user.getUsername());
    }

    @SneakyThrows
    @Test
    public void testCreateUserSuccess(){
        // Створення нового Юзера
        User userDto = User.builder()
                .name("Olena")
                .username("User-1")
                .email("email@gmail.com")
                .phone("333-333-333")
                .website("www.test.com")
                .build();

        User createdUser = userService.createUser(userDto);
        Assert.assertEquals(11, createdUser.getId());
        userDto.setId(11);
        Assert.assertEquals(userDto, createdUser);
    }

    @Test
    public void testUpdateUserSuccess(){
        User newUserInfo = User.builder()
                .id(1)
                .email("NewEmail@gmail.com")
                .phone("555-555-555")
                .build();

        User updatedUser = userService.updateUser(newUserInfo.getId(), newUserInfo);
        Assert.assertEquals(newUserInfo,updatedUser);
    }

    @Test
    public void testDeleteUserByIdSuccess(){
        int responseCode = userService.deleteUser(9);
        //будемо вважати коректним результат - статус відповіді 200
        Assert.assertEquals(200,responseCode);
    }
}

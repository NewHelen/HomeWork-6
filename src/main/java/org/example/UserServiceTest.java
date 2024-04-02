package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.user.UserDto;
import org.example.user.UserService;
import org.junit.*;
import java.net.http.HttpResponse;
import java.util.Optional;

public class UserServiceTest {

    private static final UserService userService = new UserService();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUsersSuccess (){
        HttpResponse<String> responseGetAllUsers = userService.getUsers();
        Assert.assertEquals(200,responseGetAllUsers.statusCode());
    }

    @Test
    public void testGetUserByIdSuccess(){
        HttpResponse<String> responseGetUser = userService.getUser(1);
        Assert.assertEquals(200,responseGetUser.statusCode());
    }

    @Test
    public void testGetUserByUserNameSuccess(){
        HttpResponse<String> responseGetUser = userService.getUser("Antonette");
        Assert.assertEquals(200,responseGetUser.statusCode());
    }

    @SneakyThrows
    @Test
    public void testCreateUserSuccess(){
        // Створення нового Юзера
        UserDto userDto = UserDto.builder()
                .name("Olena")
                .username("User-1")
                .email("email@gmail.com")
                .address("Main Street")
                .phone("1-770-736-8031 x56442")
                .website("www.test.com")
                .company("Crona")
                .build();
        UserDto createdUser = userService.createUser(userDto);
//        Assert.assertEquals(11, createdUser.getId());
//        userDto.setId(11);
//        Assert.assertEquals(userDto, createdUser);
    }

    @Test
    public void testUpdateUserSuccess(){
        UserDto updatedUserDto = UserDto.builder()
                .email("NewEmail@gmail.com")
                .address("New Street")
                .company("Crona")
                .build();
        HttpResponse<String> responseUpdateUser = userService.updateUser(10, updatedUserDto);
        Assert.assertEquals(200,responseUpdateUser.statusCode());
        // ? Expected updated JSON
    }

    @Test
    public void testDeleteUserByIdSuccess(){
        HttpResponse<String> responseDeleteUser = userService.deleteUser(9);
        //будемо вважати коректним результат - статус відповіді 200
        Assert.assertEquals(200,responseDeleteUser.statusCode());
    }
}

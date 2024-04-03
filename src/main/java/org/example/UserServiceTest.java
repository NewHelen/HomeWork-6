package org.example;
import lombok.SneakyThrows;
import org.example.core.Comment;
import org.example.core.User;
import org.example.core.Service;
import org.junit.*;
import java.util.List;

public class UserServiceTest {

    private static final Service SERVICE = new Service();

    //завдання 1
    @Test
    public void testGetUsersSuccess (){
        int expectedUsersCount =10;
        List<User> users = SERVICE.getUsers();
        Assert.assertEquals(expectedUsersCount, users.size());
    }

    @Test
    public void testGetUserByIdSuccess(){
        User user = SERVICE.getUser(1);
        Assert.assertEquals(1,user.getId());
    }

    @Test
    public void testGetUserByUserNameSuccess(){
        String username = "Antonette";
        User user = SERVICE.getUser(username);
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

        User createdUser = SERVICE.createUser(userDto);
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

        User updatedUser = SERVICE.updateUser(newUserInfo.getId(), newUserInfo);
        Assert.assertEquals(newUserInfo,updatedUser);
    }

    @Test
    public void testDeleteUserByIdSuccess(){
        int responseCode = SERVICE.deleteUser(9);
        //будемо вважати коректним результат - статус відповіді 200
        Assert.assertEquals(200,responseCode);
    }

    //завдання 2
    @Test
    public void testGetCommentsForPostSuccess(){
        int expectedCommentsCount =5;
        List<Comment> commentList = SERVICE.getCommentsForUserPost(1);
        Assert.assertEquals(expectedCommentsCount, commentList.size());
    }
}

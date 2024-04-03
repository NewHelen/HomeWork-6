package org.example.core;
import lombok.SneakyThrows;
import org.example.core.dto.Task;
import org.example.core.dto.User;
import org.junit.*;
import java.util.List;

public class UserServiceTest {

    private static final UserService USER_SERVICE = new UserService();

    //завдання 1
    @Test
    public void testGetUsersSuccess (){
        int expectedUsersCount =10;
        List<User> users = USER_SERVICE.getUsers();
        Assert.assertEquals(expectedUsersCount, users.size());
    }

    @Test
    public void testGetUserByIdSuccess(){
        int userId =1;
        User user = USER_SERVICE.getUser(userId);
        Assert.assertEquals(1,user.getId());
    }

    @Test
    public void testGetUserByUserNameSuccess(){
        String username = "Antonette";
        List<User> users = USER_SERVICE.getUsers(username);
        users.forEach(user -> Assert.assertEquals(username,user.getUsername()));
    }

    @SneakyThrows
    @Test
    public void testCreateUserSuccess(){
        User userDto = User.builder()
                .name("Olena")
                .username("User-1")
                .email("email@gmail.com")
                .phone("333-333-333")
                .website("www.test.com")
                .build();

        User createdUser = USER_SERVICE.createUser(userDto);
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

        User updatedUser = USER_SERVICE.updateUser(newUserInfo.getId(), newUserInfo);
        Assert.assertEquals(newUserInfo,updatedUser);
    }

    @Test
    public void testDeleteUserByIdSuccess(){
        int responseCode = USER_SERVICE.deleteUser(9);
        //будемо вважати коректним результат - статус відповіді 200
        Assert.assertEquals(200,responseCode);
    }

    //завдання 2
    @Test
    public void testGetCommentsForPostSuccess(){
        int userId =1;
        int maxPost = 10;
        String expectedFileName = "user-" + userId + "-post-" + maxPost + "-comments.json";
        String expectedDirectory = "target/";

        USER_SERVICE.getCommentsForUserPost(userId,expectedDirectory);
        Assert.assertTrue("The file was not created in the expected folder",
                new java.io.File(expectedDirectory + expectedFileName).exists());
    }

    //завдання 3
    @Test
    public void testGetOpenTasksSuccess(){
        int expectedTasksCount = 9;
        List<Task> filteredTasks = USER_SERVICE.getOpenUserTasks(1);
        Assert.assertEquals("Filtered tasks size does not equal to expected size",
                expectedTasksCount, filteredTasks.size());

    }
}

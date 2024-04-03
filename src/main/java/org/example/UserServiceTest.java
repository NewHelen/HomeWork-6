package org.example;
import lombok.SneakyThrows;
import org.example.core.Comment;
import org.example.core.Task;
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
        int userId =1;
        User user = SERVICE.getUser(userId);
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
        int userId =1;
        String expectedFolderPath = "files";

        String fileName = SERVICE.getCommentsForUserPost(userId);
        String filePath = expectedFolderPath + "/" + fileName;

        java.io.File file = new java.io.File(filePath);
        Assert.assertTrue("The file was not created in the expected folder", file.exists());
        Assert.assertEquals("The file was not created in the expected folder", expectedFolderPath, file.getParent());
    }

    //завдання 3
    @Test
    public void testGetOpenTasksSuccess(){
        boolean isCompleted = false;
        int expectedTasksCount = 9;
        List<Task> filteredTasks = SERVICE.getUserTasks(1,isCompleted);
        Assert.assertEquals("Filtered tasks size does not equal to expected size",expectedTasksCount, filteredTasks.size());

    }
}

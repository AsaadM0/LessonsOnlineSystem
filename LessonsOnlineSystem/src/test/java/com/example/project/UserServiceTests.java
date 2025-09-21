
package com.example.project;
/*import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserService;*/
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

   /*  @Autowired
   private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRegisterAndLogin() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("1234");
        userService.register(user);

        assertTrue(userService.login("test@example.com", "1234").isPresent());
        assertFalse(userService.login("test@example.com", "wrongpass").isPresent());
    }*/
}

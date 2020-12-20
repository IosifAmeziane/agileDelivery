package sda.projectManagementTool.projectManagement.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.repository.model.UserType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User("username", "email@gmail.com", "passowrd");
        User db = userRepository.save(user);
        assertNotNull(db.getId());
    }

    @Test
    public void testCreateUserExpectToFailWithWronglyFormattedEmail() {
        User user = new User("username", "email", "passowrd");
        Exception exception = assertThrows(Exception.class, () -> {
            userRepository.save(user);
        });
        assertNotNull(exception);
    }

    @Test
    public void testCreateUserExpectToFailWithDuplicateException() {
        User user = new User("username", "email@gmail.com", "passowrd");
        User db = userRepository.save(user);
        User user2 = new User("username", "email@gmail.com", "passowrd");
        Exception exception = assertThrows(Exception.class, () -> {
            userRepository.save(user2);
        });

        assertNotNull(exception);
    }

}

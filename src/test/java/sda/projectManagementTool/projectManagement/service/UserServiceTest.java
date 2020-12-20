package sda.projectManagementTool.projectManagement.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sda.projectManagementTool.projectManagement.repository.UserRepository;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.repository.model.UserType;
import sda.projectManagementTool.projectManagement.service.exception.UserAlreadyPresentException;
import sda.projectManagementTool.projectManagement.service.exception.UserNotFoundException;
import sda.projectManagementTool.projectManagement.service.implementation.UserServiceImplementation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImplementation userServiceImplementation;

    @Mock
    UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User("usernmae", "email@gmail.com", "password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);
        userServiceImplementation.save(user);
        Assertions.assertNotNull(user);
    }

    @Test
    public void testCreateUserExpectUserAlreadyPresentException() {
        User user = new User("usernmae", "email@gmail.com", "password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);
        Exception exception = assertThrows(UserAlreadyPresentException.class, () -> {
            userServiceImplementation.save(user);
        });
        System.out.println(exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    public void testFindByIdExpectedAnUserToBeReturned() {
        User user = new User("usernmae", "email@gmail.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User userFromService = userServiceImplementation.findById(1L);
        assertNotNull(userFromService);
    }

    @Test
    public void testFindByIdExpectUserNotFoundException() {
        User user = new User("usernmae", "email@gmail.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userServiceImplementation.findById(2L);
        });
        System.out.println(exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    public void testDeleteUserByIdExpectSuccess() {
        User user = new User("usernmae", "email@gmail.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userServiceImplementation.delete(1L);
    }

    @Test
    public void testDeleteUserByIdExceptUserNotFoundException() {
        User user = new User("usernmae", "email@gmail.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userServiceImplementation.delete(2L);
        });
        System.out.println(exception.getMessage());
        assertNotNull(exception);
    }

}


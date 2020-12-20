package sda.projectManagementTool.projectManagement.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sda.projectManagementTool.projectManagement.ProjectManagementApplication;
import sda.projectManagementTool.projectManagement.repository.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest ( classes = ProjectManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetUser() {
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity("http://localhost:" + port + "/users/1", User.class);
        Assertions.assertNotNull(userResponseEntity);
    }

    @Test
    public void testGetUserWhichNotExists() {
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity("http://localhost:" + port + "/users/125", User.class);
        Assertions.assertNotNull(userResponseEntity);
    }

}

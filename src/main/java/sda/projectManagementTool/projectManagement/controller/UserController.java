package sda.projectManagementTool.projectManagement.controller;

import org.springframework.web.bind.annotation.*;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(path = "/users/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}

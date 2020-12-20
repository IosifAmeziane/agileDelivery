package sda.projectManagementTool.projectManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.UserService;

@RestController
public class EmailConfirmationController {

    private UserService userService;

    public EmailConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/confirm/{confirmationToken}")
    public User confirmationOfEmail(@PathVariable String confirmationToken) {
        return userService.confirmUser(confirmationToken);
    }

}

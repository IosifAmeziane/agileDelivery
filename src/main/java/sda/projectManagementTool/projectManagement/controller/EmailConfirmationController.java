package sda.projectManagementTool.projectManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.UserService;

import javax.persistence.GeneratedValue;

@RestController
public class EmailConfirmationController {

    private UserService userService;

    public EmailConfirmationController(UserService userService) {
        this.userService = userService;
    }

    // userRegiter - > se trimite un email cu confrimation token de genul
    // http://localhost:8081/confirm/{token}
    // Cand utilizatorul da click pe link-ul de mai sus, se apeleaza metoda
    // confirmationOfEmail care este prezenta mai jos.
    //
    //
    //
    @GetMapping(path = "/confirm/{confirmationToken}")
    public User confirmationOfEmail(@PathVariable String confirmationToken) {
        return userService.confirmUser(confirmationToken);
    }

}

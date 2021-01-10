package sda.projectManagementTool.projectManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sda.projectManagementTool.projectManagement.dto.UserDto;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User save(@RequestBody UserDto userDto) {
        User user = userService.mapUserDtoToUser(userDto);
        return userService.save(user);
    }

    @GetMapping(path = "/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public User findByEmail(@RequestParam("email") String email){
        return userService.findByEmail(email);
    }

    @GetMapping(path = "/logged-user")
    public HttpStatus getLoggedUser() {
        return HttpStatus.OK;
    }
}

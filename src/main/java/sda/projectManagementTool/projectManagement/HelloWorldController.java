package sda.projectManagementTool.projectManagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello-world-for-project-manager")
    public String getHelloWorld() {
        return "Hello World for project manager";
    }

    @GetMapping(path = "/hello-world-for-dev")
    public String getHelloWorldForDev() {
        return "Hello World for developer";
    }

}

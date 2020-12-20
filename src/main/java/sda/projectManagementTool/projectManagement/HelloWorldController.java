package sda.projectManagementTool.projectManagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello-dev")
    public String getHelloWorld() {
        return "Hello World Developer";
    }

    @GetMapping(path = "/hello-pm")
    public String getHelloWorldProjectManager() {
        return "Hello World Project Manager";
    }
}

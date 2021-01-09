package sda.projectManagementTool.projectManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sda.projectManagementTool.projectManagement.repository.RoleRepository;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.repository.model.Role;
import sda.projectManagementTool.projectManagement.repository.model.UserType;

import java.util.Arrays;

@SpringBootApplication
public class ProjectManagementApplication implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Arrays
                .stream(UserType.values()) // DEVELOPER, PROJECT_MANAGER;
                .forEach(userType -> {
                    if (roleRepository.findByRole(userType.name()) == null) {
						roleRepository.save(new Role(userType.name()));
                    }
                });

    }
}

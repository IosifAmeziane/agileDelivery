package sda.projectManagementTool.projectManagement.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sda.projectManagementTool.projectManagement.dto.ProjectDtoRequest;
import sda.projectManagementTool.projectManagement.repository.ProjectRepository;
import sda.projectManagementTool.projectManagement.repository.UserRepository;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.EmailService;
import sda.projectManagementTool.projectManagement.service.ProjectService;
import sda.projectManagementTool.projectManagement.service.exception.ResourceAlreadyPresentException;
import sda.projectManagementTool.projectManagement.service.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    public ProjectServiceImplementation(ProjectRepository projectRepository, UserRepository userRepository, EmailService emailService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public Project save(Project project) {

        // obiectul de principal contine detalii despre utilizatorul autentificat in aplicatie
        // Cand ne autentificam in app se creaza un context de securitate unde se tin toate
        // detaliile necesare autentifcarii

        // Obiectul de principal

        if (projectRepository.findByName(project.getName()) == null) {
            org.springframework.security.core.userdetails.User principal =
                    (org.springframework.security.core.userdetails.User) SecurityContextHolder
                                                .getContext() //luam context-ul
                                                .getAuthentication() //luam autentificarea
                                                .getPrincipal();  //luam user-ul logat

            String loggedUsername = principal.getUsername();
            User user = userRepository.findByUsername(loggedUsername);
            project.setAdministrator(user);
            return projectRepository.save(project);
        } else {
            throw new ResourceAlreadyPresentException(
                    String
                            .format("Resource of type %s already exists in db with name %s",
                                    "project",
                                    project.getName()));
        }
    }

    @Override
    public Project findByName(String name) {
        Project project = projectRepository.findByName(name);
        if (project != null) {
            return project;
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with name %s not found", "project", name));
        }
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with name %d not found", "project", id));
        }
    }

    @Override
    public void delete(Long id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "project", id));
        }
    }

    @Override
    public Project update(ProjectDtoRequest updatedProject, Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project projectInDatabase = project.get();
            if (updatedProject.getDescription() != null) {
                projectInDatabase.setDescription(updatedProject.getDescription());
            }
            if (updatedProject.getName() != null) {
                projectInDatabase.setName(updatedProject.getName());
            }
            return projectRepository.save(projectInDatabase);
        } else {
            throw new ResourceNotFoundException(String.format("Resource %s with id %d not found", "project", id));
        }
    }

    @Override
    public Project assignUserToProject(String username, String projectName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "project", projectName));
        }
        List<User> assignedUsers = project.getAssignedUsers();
        assignedUsers.add(user);
        project.setAssignedUsers(assignedUsers);
        Project updatedProject = projectRepository.save(project);
        emailService.sendEmail(user.getEmail(), String.format("You have been assigned to project with name %s", project.getName()));
        return updatedProject;
    }

    @Override
    public List<Project> findAll() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public void sendInviteLinkToUserForProjectAssignment(String username, String projectName) {
        if (isUserAndProjectPresent(username, projectName)) {
            User user = userRepository.findByUsername(username);
            // http://localhost:8081/projects/users?projectName=projectName&username=username;
            emailService
                    .sendEmail(user.getEmail(),
                               String.format("You have been invited to the project. Please " +
                                               "access the link to be assigned. " +
                                               "http://localhost:8081/projects/users?username=%s&projectName=%s",
                               username, projectName));

        }
    }

    @Override
    public Page<Project> findAllPaginated(int size, int page) {
        return projectRepository.findAll(PageRequest.of(page,size));
    }

    private boolean isUserAndProjectPresent(String username, String projectName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "user", username));
        }
        Project project = projectRepository.findByName(projectName);
        if (project == null) {
            throw new ResourceNotFoundException(String.format("Resource %s with id %s not found", "project", projectName));
        }
        return true;
    }
}

package sda.projectManagementTool.projectManagement.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sda.projectManagementTool.projectManagement.dto.ProjectDtoRequest;
import sda.projectManagementTool.projectManagement.dto.ProjectDtoResponse;
import sda.projectManagementTool.projectManagement.dto.MinimumUserInfoDto;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.EmailService;
import sda.projectManagementTool.projectManagement.service.ProjectService;
import sda.projectManagementTool.projectManagement.util.MappingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProjectController {

    private ProjectService projectService;
    private EmailService emailService;

    public ProjectController(ProjectService projectService, EmailService emailService) {
        this.projectService = projectService;
        this.emailService = emailService;
    }

    @PostMapping(path = "/projects")
    public ProjectDtoResponse save(@RequestBody ProjectDtoRequest projectRequest) {

        // --- conversie de la Request la Entity
        Project projectRequestEntity = mapProjectRequestDtoToProject(projectRequest);
        // --- are loc actiunea (in cazul salvarea in db
        Project project = projectService.save(projectRequestEntity);
        // --- conversie de la entity la response
        return mapProjectToProjectDtoResponse(project);
    }

    @GetMapping(path = "/projects/{id}")
    public ProjectDtoResponse getById(@PathVariable Long id) {
        return mapProjectToProjectDtoResponse(projectService.findById(id));
    }

    @DeleteMapping (path = "/projects/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        projectService.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping(path = "/projects")
    public List<ProjectDtoResponse> getAll() {
        List<Project> projectList = projectService.findAll();
        List<ProjectDtoResponse> projectDtoResponses = new ArrayList<>();
        projectList.forEach(project -> projectDtoResponses.add(mapProjectToProjectDtoResponse(project)));
        return projectDtoResponses;
    }

    @PutMapping(path = "/projects/{id}")
    public ProjectDtoResponse update(@RequestBody ProjectDtoRequest projectDtoRequest, @PathVariable Long id) {
        Project project =  projectService.update(projectDtoRequest, id);
        return mapProjectToProjectDtoResponse(project);
    }


    // http://localhost:8081/projects/users?[projectName=Project1]&[username=developer1]
    @GetMapping(path = "/projects/users")
    public ProjectDtoResponse update(@RequestParam("projectName") String projectName, @RequestParam("username") String username) {
        Project project = projectService.assignUserToProject(username, projectName);
        return mapProjectToProjectDtoResponse(project);
    }

    @GetMapping(path = "/projects/assign-users")
    public HttpStatus invite(@RequestParam("projectName") String projectName, @RequestParam("username") String username) {
        projectService.sendInviteLinkToUserForProjectAssignment(username, projectName);
        return HttpStatus.OK;
    }

    private Project mapProjectRequestDtoToProject(ProjectDtoRequest projectDtoRequest) {
        Project project = new Project();
        project.setDescription(projectDtoRequest.getDescription());
        project.setName(projectDtoRequest.getName());
        return project;
    }

    @GetMapping(path = "/paginated-projects")
    public Page<Project> getAllPaginated(@RequestParam("size") int size, @RequestParam ("page") int page) {
        return projectService.findAllPaginated(size, page);
    }

    private ProjectDtoResponse mapProjectToProjectDtoResponse(Project project) {
        MinimumUserInfoDto minimumUserInfoDto = MappingUtils.mapUserToUserDetailsProjectDto(project.getUser());
        List<MinimumUserInfoDto> assignedUsers = new ArrayList<>();
        project.getAssignedUsers().forEach( user -> {
            assignedUsers.add(MappingUtils.mapUserToUserDetailsProjectDto(user));
        });
        ProjectDtoResponse response = new ProjectDtoResponse(project.getId(), project.getDescription(), project.getName(), minimumUserInfoDto, assignedUsers);
        response.setSprintNumbers(project.getAgileSprints().size());
        return response;
    }
}

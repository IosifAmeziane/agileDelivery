package sda.projectManagementTool.projectManagement.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sda.projectManagementTool.projectManagement.dto.AgileStoryDtoRequest;
import sda.projectManagementTool.projectManagement.dto.AgileStoryDtoResponse;
import sda.projectManagementTool.projectManagement.dto.MinimumUserInfoDto;
import sda.projectManagementTool.projectManagement.repository.model.AgileStory;
import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.AgileStoryService;
import sda.projectManagementTool.projectManagement.service.ProjectService;
import sda.projectManagementTool.projectManagement.service.UserService;
import sda.projectManagementTool.projectManagement.service.exception.ResourceNotFoundException;
import sda.projectManagementTool.projectManagement.util.MappingUtils;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AgileStoryController {

    private AgileStoryService agileStoryService;
    private ProjectService projectService;
    private UserService userService;

    public AgileStoryController(AgileStoryService agileStoryService, UserService userService, ProjectService projectService) {
        this.agileStoryService = agileStoryService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @PostMapping(path = "/agile-stories")
    public AgileStoryDtoResponse save(@RequestBody AgileStoryDtoRequest agileStoryDtoRequest) {
        AgileStory agileStory = mapAgileStoryDtoRequestToAgileStory(agileStoryDtoRequest);
        AgileStory savedInDb = agileStoryService.save(agileStory);
        return mapAgileEntityToAgileDtoResponse(savedInDb);
    }

    @GetMapping(path = "/agile-stories/{id}")
    public AgileStoryDtoResponse getById(@PathVariable Long id) {
        return mapAgileEntityToAgileDtoResponse(agileStoryService.findById(id));
    }


    @PutMapping(path = "/agile-stories/{id}")
    public AgileStoryDtoResponse assignStoryToUser(@PathVariable Long id, @RequestParam("username") String username) {
        AgileStory agileStory = agileStoryService.findById(id);
        return mapAgileEntityToAgileDtoResponse(agileStoryService.assignStoryToUser(agileStory, username));
    }

    @PutMapping(path = "/agile-stories/status/{id}")
    public AgileStoryDtoResponse updateStatus(@PathVariable Long id, @RequestParam("status") AgileStoryStatus status) {
        return mapAgileEntityToAgileDtoResponse(agileStoryService.updateStatus(id, status));
    }

    @GetMapping(path = "/agile-stories/all")
    public List<AgileStoryDtoResponse> findAll() {
        List<AgileStory> agileStories = agileStoryService.findAll();

        List<AgileStoryDtoResponse> responseList = new ArrayList<>();
        agileStories.forEach(agileStory -> {
            responseList.add(mapAgileEntityToAgileDtoResponse(agileStory));
        });
        return responseList;
    }
    @GetMapping(path = "/agile-stories")
    public List<AgileStoryDtoResponse> findAllByName(@RequestParam("name") String name, @RequestParam("projectId") Long projectId) {
        List<AgileStory> agileStories = agileStoryService.findByNameContainsAndProjectId(name, projectId);
        List<AgileStoryDtoResponse> responseList = new ArrayList<>();
        agileStories.forEach(agileStory -> {
            responseList.add(mapAgileEntityToAgileDtoResponse(agileStory));
        });
        return responseList;
    }

    @GetMapping(path = "/agile-stories/project/{id}")
    public List<AgileStoryDtoResponse> findAllByProjectId(@PathVariable("id") Long projectId) {
        List<AgileStory> agileStories = agileStoryService.findAllByProjectId(projectId);
        List<AgileStoryDtoResponse> responseList = new ArrayList<>();
        agileStories.forEach(agileStory -> {
            responseList.add(mapAgileEntityToAgileDtoResponse(agileStory));
        });
        return responseList;
    }

    @GetMapping(path = "/agile-stories/sprints/{id}")
    public List<AgileStoryDtoResponse> findAllStoriesBySprint(@PathVariable("id") Long sprintId) {
        List<AgileStory> agileStories = agileStoryService.findAllBySprintId(sprintId);
        List<AgileStoryDtoResponse> responseList = new ArrayList<>();
        agileStories.forEach(agileStory -> {
            responseList.add(mapAgileEntityToAgileDtoResponse(agileStory));
        });
        return responseList;
    }
    private AgileStory mapAgileStoryDtoRequestToAgileStory(AgileStoryDtoRequest agileStoryDtoRequest) {
        AgileStory agileStory = new AgileStory();

        agileStory.setWeight(agileStoryDtoRequest.getWeight());
        agileStory.setName(agileStoryDtoRequest.getName());
        agileStory.setDescription(agileStoryDtoRequest.getDescription());
        agileStory.setStatus(agileStoryDtoRequest.getStatus());
        agileStory.setStoryPoints(agileStoryDtoRequest.getStoryPoints());
        if (agileStoryDtoRequest.getUsername() != null) {
            User user = userService.findByUsername(agileStoryDtoRequest.getUsername());
            agileStory.setAssignedUser(user);
        } else {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findByUsername(principal.getUsername());
            agileStory.setAssignedUser(user);
        }

        Project project = projectService.findById(agileStoryDtoRequest.getProjectId());
        if ( project != null) {
            agileStory.setProject(project);
        } else {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", agileStoryDtoRequest.getProjectId()));
        }

        return agileStory;
    }

    private AgileStoryDtoResponse mapAgileEntityToAgileDtoResponse(AgileStory agileStory){
        AgileStoryDtoResponse agileStoryDtoResponse = new AgileStoryDtoResponse();
        agileStoryDtoResponse.setName(agileStory.getName());
        agileStoryDtoResponse.setDescription(agileStory.getDescription());
        agileStoryDtoResponse.setStatus(agileStory.getStatus());
        agileStoryDtoResponse.setId(agileStory.getId());
        agileStoryDtoResponse.setStoryPoints(agileStory.getStoryPoints());
        agileStoryDtoResponse.setWeight(agileStory.getWeight());

        MinimumUserInfoDto infoDto = MappingUtils.mapUserToUserDetailsProjectDto(agileStory.getAssignedUser());
        agileStoryDtoResponse.setUserInfo(infoDto);
        return agileStoryDtoResponse;
    }
}

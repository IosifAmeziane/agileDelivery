package sda.projectManagementTool.projectManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sda.projectManagementTool.projectManagement.dto.AgileSprintDtoRequest;
import sda.projectManagementTool.projectManagement.dto.AgileSprintDtoResponse;
import sda.projectManagementTool.projectManagement.dto.AgileSprintProjectDtoResponse;
import sda.projectManagementTool.projectManagement.dto.AgileSprintStoryDtoResponse;
import sda.projectManagementTool.projectManagement.repository.model.AgileSprint;
import sda.projectManagementTool.projectManagement.repository.model.AgileStory;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.service.AgileSprintService;
import sda.projectManagementTool.projectManagement.service.AgileStoryService;
import sda.projectManagementTool.projectManagement.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/agile-sprints")
public class AgileSprintController {

    private AgileSprintService agileSprintService;
    private ProjectService projectService;
    private AgileStoryService agileStoryService;

    public AgileSprintController(AgileSprintService agileSprintService, ProjectService projectService, AgileStoryService agileStoryService) {
        this.agileSprintService = agileSprintService;
        this.projectService = projectService;
        this.agileStoryService = agileStoryService;
    }

    @PostMapping
    public AgileSprintDtoResponse save(@RequestBody AgileSprintDtoRequest agileSprintDtoRequest) {
        AgileSprint agileSprint = mapAgileSprintDtoRequestToAgileSprint(agileSprintDtoRequest);
        AgileSprint agileSprintDb = agileSprintService.save(agileSprint);
        return mapAgileSprintEntityToDtoResponse(agileSprintDb);
    }

    @GetMapping
    public List<AgileSprintDtoResponse> getAgileSprintsByProjectId(@RequestParam("projectId") Long projectId) {
        List<AgileSprint> agileSprints = agileSprintService.findAllSprintsByProjectId(projectId);
        List<AgileSprintDtoResponse> agileSprintDtoResponses = mapAgileSprintListToDtoResponseList(agileSprints);
        return agileSprintDtoResponses;
    }

    private List<AgileSprintDtoResponse> mapAgileSprintListToDtoResponseList(List<AgileSprint> agileSprints) {
        List<AgileSprintDtoResponse> agileSprintDtoResponses = new ArrayList<>();
        agileSprints.forEach(agileSprint -> {
            agileSprintDtoResponses.add(mapAgileSprintEntityToDtoResponse(agileSprint));
        });
        return agileSprintDtoResponses;
    }

    // /{filtering} e inlocuit fie de "completed", fie de "future"
    @GetMapping(path = "/{filtering}")
    public List<AgileSprintDtoResponse> getCompletedAgileSprintsByProjectId(@PathVariable("filtering") String filtering, @RequestParam("projectId") Long projectId) {
        List<AgileSprint> agileSprints = new ArrayList<>();
        if (filtering.equals("completed")) {
            agileSprints = agileSprintService.findAllCompletedSprintsByProjectId(projectId);
        } else if (filtering.equals("future")){
            agileSprints = agileSprintService.findSprintsByProjectIdWhichStartInFuture(projectId);
        }
        return mapAgileSprintListToDtoResponseList(agileSprints);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        agileSprintService.delete(id);
        return HttpStatus.OK;
    }

    private AgileSprint mapAgileSprintDtoRequestToAgileSprint(AgileSprintDtoRequest agileSprintDtoRequest) {
        AgileSprint agileSprint = new AgileSprint();
        agileSprint.setStartDate(agileSprintDtoRequest.getStartDate());
        agileSprint.setEndDate(agileSprintDtoRequest.getEndDate());
        Project project = projectService.findById(agileSprintDtoRequest.getProjectId());
        agileSprint.setProject(project);
        List<AgileStory> agileStoryList = agileStoryService.findByIds(agileSprintDtoRequest.getStoryIds());
        agileSprint.setAgileStories(agileStoryList);
        return agileSprint;
    }

    private AgileSprintDtoResponse mapAgileSprintEntityToDtoResponse(AgileSprint agileSprint) {
        AgileSprintDtoResponse response = new AgileSprintDtoResponse();
        response.setStartDate(agileSprint.getStartDate());
        response.setEndDate(agileSprint.getEndDate());
        response.setId(agileSprint.getId());
        response.setProjectDto(mapProjectEntityToAgileSprintDto(agileSprint.getProject()));
        List<AgileSprintStoryDtoResponse> agileSprintStoryDtoResponses = new ArrayList<>();
        agileSprint.getAgileStories().forEach(agileStory -> {
            agileSprintStoryDtoResponses.add(mapAgileStoryEntityToStoryDtoResponse(agileStory));
        });
        response.setAgileStoriesDto(agileSprintStoryDtoResponses);
        return response;
    }

    private AgileSprintProjectDtoResponse mapProjectEntityToAgileSprintDto(Project project) {
        AgileSprintProjectDtoResponse response = new AgileSprintProjectDtoResponse();
        response.setName(project.getName());
        response.setId(project.getId());
        return response;
    }

    private AgileSprintStoryDtoResponse mapAgileStoryEntityToStoryDtoResponse(AgileStory agileStory) {
        AgileSprintStoryDtoResponse response = new AgileSprintStoryDtoResponse();
        response.setAgileStoryStatus(agileStory.getStatus());
        response.setName(agileStory.getName());
        response.setId(agileStory.getId());
        response.setAssignedUser(agileStory.getAssignedUser().getUsername());
        return response;
    }

}

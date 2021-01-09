package sda.projectManagementTool.projectManagement.dto;

import java.time.LocalDate;
import java.util.List;

public class AgileSprintDtoResponse {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private AgileSprintProjectDtoResponse projectDto;
    private List<AgileSprintStoryDtoResponse> agileStoriesDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AgileSprintProjectDtoResponse getProjectDto() {
        return projectDto;
    }

    public void setProjectDto(AgileSprintProjectDtoResponse projectDto) {
        this.projectDto = projectDto;
    }

    public List<AgileSprintStoryDtoResponse> getAgileStoriesDto() {
        return agileStoriesDto;
    }

    public void setAgileStoriesDto(List<AgileSprintStoryDtoResponse> agileStoriesDto) {
        this.agileStoriesDto = agileStoriesDto;
    }
}

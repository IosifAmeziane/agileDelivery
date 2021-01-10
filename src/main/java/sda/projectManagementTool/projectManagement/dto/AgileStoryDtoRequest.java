package sda.projectManagementTool.projectManagement.dto;

import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;

public class AgileStoryDtoRequest {
    private Integer storyPoints;
    private String name;
    private String description;
    private AgileStoryStatus status;
    private String username;
    private int weight;
    private Long projectId;


    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AgileStoryStatus getStatus() {
        return status;
    }

    public void setStatus(AgileStoryStatus status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}

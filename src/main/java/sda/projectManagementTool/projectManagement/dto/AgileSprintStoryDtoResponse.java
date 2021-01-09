package sda.projectManagementTool.projectManagement.dto;

import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;

public class AgileSprintStoryDtoResponse {
    private Long id;
    private AgileStoryStatus agileStoryStatus;
    private String assignedUser;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgileStoryStatus getAgileStoryStatus() {
        return agileStoryStatus;
    }

    public void setAgileStoryStatus(AgileStoryStatus agileStoryStatus) {
        this.agileStoryStatus = agileStoryStatus;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

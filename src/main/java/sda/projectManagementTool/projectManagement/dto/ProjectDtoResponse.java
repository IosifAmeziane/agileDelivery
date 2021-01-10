package sda.projectManagementTool.projectManagement.dto;

import java.util.List;

public class ProjectDtoResponse {
    private Long id;
    private String description;
    private String name;
    private MinimumUserInfoDto administrator;
    private List<MinimumUserInfoDto> assignedUsers;
    private int sprintNumbers;

    public ProjectDtoResponse(Long id, String description, String name,
                              MinimumUserInfoDto administrator,
                              List<MinimumUserInfoDto> assignedUsers) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.administrator = administrator;
        this.assignedUsers = assignedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MinimumUserInfoDto getAdministrator() {
        return administrator;
    }

    public void setAdministrator(MinimumUserInfoDto administrator) {
        this.administrator = administrator;
    }

    public List<MinimumUserInfoDto> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<MinimumUserInfoDto> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public int getSprintNumbers() {
        return sprintNumbers;
    }

    public void setSprintNumbers(int sprintNumbers) {
        this.sprintNumbers = sprintNumbers;
    }
}

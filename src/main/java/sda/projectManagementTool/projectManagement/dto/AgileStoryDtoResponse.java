package sda.projectManagementTool.projectManagement.dto;

import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;

public class AgileStoryDtoResponse {
    private Long id;
    private Integer storyPoints;
    private String name;
    private String description;
    private AgileStoryStatus status;
    private MinimumUserInfoDto userInfo;
    private int weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public MinimumUserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(MinimumUserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

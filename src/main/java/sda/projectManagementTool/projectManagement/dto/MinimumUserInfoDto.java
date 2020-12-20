package sda.projectManagementTool.projectManagement.dto;

import java.util.List;

public class MinimumUserInfoDto {
    private Long id;
    private String name;
    private String emailAddress;
    private List<String> roles;

    public MinimumUserInfoDto(Long id, String name, String emailAddress, List<String> roles) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

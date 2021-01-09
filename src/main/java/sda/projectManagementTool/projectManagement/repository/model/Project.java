package sda.projectManagementTool.projectManagement.repository.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @Column (unique = true)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "project_users", inverseJoinColumns = @JoinColumn(name = "user_id"), joinColumns = @JoinColumn(name = "project_id"))
    private List<User> assignedUsers = new ArrayList<>();

    public Project() {

    }

    public Project(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setAdministrator(User user) {
        this.user = user;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
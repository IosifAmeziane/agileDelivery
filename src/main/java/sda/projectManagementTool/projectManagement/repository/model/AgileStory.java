package sda.projectManagementTool.projectManagement.repository.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class AgileStory {

    @Id
    @GeneratedValue
    private Long id;

    //TODO: add Sprint once implemeneted

    private Integer storyPoints;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private AgileStoryStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    //TODO: sa verificam cum putem afisa mesajul din annotare cand conditia nu este respectata
    @Min(value = 1, message = "The weight value should be above >= 1")
    @Max(value = 5, message = "The weight value should be less <= 5")
    private int weight;

    @ManyToOne
    private Project project;

    @ManyToOne
    private AgileSprint agileSprint;

    public AgileStory() {

    }

    public AgileSprint getAgileSprint() {
        return agileSprint;
    }

    public void setAgileSprint(AgileSprint agileSprint) {
        this.agileSprint = agileSprint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgileStoryStatus getStatus() {
        return status;
    }

    public void setStatus(AgileStoryStatus status) {
        this.status = status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public Integer getStoryPoints() {
        return storyPoints;
    }

    @Override
    public String toString() {
        return "AgileStory{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

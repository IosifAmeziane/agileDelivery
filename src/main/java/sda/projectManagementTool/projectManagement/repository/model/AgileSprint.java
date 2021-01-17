package sda.projectManagementTool.projectManagement.repository.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AgileSprint {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat
    private LocalDate startDate;

    @DateTimeFormat
    private LocalDate endDate;

    @OneToMany (mappedBy = "agileSprint")
    private List<AgileStory> agileStories;

    @ManyToOne
    @JoinColumn (name = "project_id")
    private Project project;

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

    public List<AgileStory> getAgileStories() {
        return agileStories;
    }

    public void setAgileStories(List<AgileStory> agileStories) {
        agileStories.forEach( el -> el.setAgileSprint(this));
        this.agileStories = agileStories;
    }

    public void setAgileStory(AgileStory agileStory) {
        this.agileStories.add(agileStory);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "AgileSprint{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", agileStories=" + agileStories +
                ", project=" + project +
                '}';
    }
}

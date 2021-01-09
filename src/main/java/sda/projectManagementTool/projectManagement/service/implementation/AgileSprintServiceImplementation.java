package sda.projectManagementTool.projectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sda.projectManagementTool.projectManagement.repository.AgileSprintRepository;
import sda.projectManagementTool.projectManagement.repository.AgileStoryRepository;
import sda.projectManagementTool.projectManagement.repository.ProjectRepository;
import sda.projectManagementTool.projectManagement.repository.model.AgileSprint;
import sda.projectManagementTool.projectManagement.repository.model.AgileStory;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.service.AgileSprintService;
import sda.projectManagementTool.projectManagement.service.exception.OverlappingSprintException;
import sda.projectManagementTool.projectManagement.service.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AgileSprintServiceImplementation implements AgileSprintService {

    private AgileSprintRepository agileSprintRepository;
    private AgileStoryRepository agileStoryRepository;
    private ProjectRepository projectRepository;

    public AgileSprintServiceImplementation(AgileSprintRepository agileSprintRepository, AgileStoryRepository agileStoryRepository, ProjectRepository projectRepository) {
        this.agileSprintRepository = agileSprintRepository;
        this.agileStoryRepository = agileStoryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public AgileSprint save(AgileSprint agileSprint) {
        List<AgileSprint> agileSprints = agileSprintRepository.getAgileSprintsInIntervalAndWithProjectId(agileSprint.getStartDate(), agileSprint.getEndDate(), agileSprint.getProject().getId());
        if (agileSprints.size() >= 1) {
            throw new OverlappingSprintException("There is already a sprint within the interval");
        }
        return agileSprintRepository.save(agileSprint);
    }

    @Override
    public AgileSprint assignStoriesToSprint(Long agileStoryId, Long agileSprintId) {
        Optional<AgileStory> optionalAgileStory = agileStoryRepository.findById(agileStoryId);
        if (!optionalAgileStory.isPresent()) {
            throw new ResourceNotFoundException(String.format("Agile story with id %d not found", agileStoryId));
        }
        Optional<AgileSprint> optionalAgileSprint = agileSprintRepository.findById(agileSprintId);
        if (!optionalAgileSprint.isPresent()) {
            throw new ResourceNotFoundException(String.format("Agile sprint with id %d not found", agileSprintId));
        }

        AgileSprint agileSprint = optionalAgileSprint.get();
        agileSprint.setAgileStory(optionalAgileStory.get());
        return agileSprintRepository.save(agileSprint);
    }

    @Override
    public List<AgileSprint> findAllSprintsByProjectId(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return agileSprintRepository.findAgileSprintByProjectId(projectId);
    }

    @Override
    public List<AgileSprint> findAllCompletedSprintsByProjectId(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return agileSprintRepository.getAgileSprintCompletedByProjectId(LocalDate.now(), projectId);
    }

    @Override
    public List<AgileSprint> findSprintsByProjectIdWhichStartInFuture(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException(String.format("Project with id %d not found", projectId));
        }
        return agileSprintRepository.getAgileSprintsNotStartedByProjectId(LocalDate.now(), projectId);
    }

    @Override
    public void delete(Long id) {
        Optional<AgileSprint> optionalAgileSprint = agileSprintRepository.findById(id);
        if (!optionalAgileSprint.isPresent()) {
            throw new ResourceNotFoundException(String.format("Agile sprint with id %d not found", id));
        }

        agileSprintRepository.deleteById(id);
    }
}

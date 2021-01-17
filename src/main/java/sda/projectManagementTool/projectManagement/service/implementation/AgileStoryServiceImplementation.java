package sda.projectManagementTool.projectManagement.service.implementation;

import org.springframework.stereotype.Service;
import sda.projectManagementTool.projectManagement.repository.AgileStoryRepository;
import sda.projectManagementTool.projectManagement.repository.UserRepository;
import sda.projectManagementTool.projectManagement.repository.model.AgileStory;
import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.AgileStoryService;
import sda.projectManagementTool.projectManagement.service.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgileStoryServiceImplementation implements AgileStoryService {

    private AgileStoryRepository agileStoryRepository;
    private UserRepository userRepository;

    public AgileStoryServiceImplementation(AgileStoryRepository agileStoryRepository, UserRepository userRepository) {
        this.agileStoryRepository = agileStoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AgileStory save(AgileStory agileStory) {
        return agileStoryRepository.save(agileStory);
    }

    @Override
    public void delete(Long id) {
        Optional<AgileStory> agileStory = agileStoryRepository.findById(id);
        if (agileStory.isPresent()) {
            agileStoryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("Resourse of type %s with id %d  was not found", "agileStory", id));
        }
    }

    @Override
    public AgileStory findById(Long id) {
        Optional<AgileStory> agileStory = agileStoryRepository.findById(id);
        if (agileStory.isPresent()) {
           return agileStory.get();
        } else {
            throw new ResourceNotFoundException(String.format("Resourse of type %s with id %d  was not found", "agileStory", id));
        }
    }

    @Override
    public AgileStory assignStoryToUser(AgileStory agileStory, String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            agileStory.setAssignedUser(user);
            return agileStoryRepository.save(agileStory);
        } else {
            throw new ResourceNotFoundException(String.format("Resourse of type %s with id %s  was not found", "user", username));
        }
    }

    @Override
    public AgileStory updateStatus(Long id, AgileStoryStatus newStatus) {
        AgileStory agileStory = findById(id);
        agileStory.setStatus(newStatus);
        return agileStoryRepository.save(agileStory);
    }

    @Override
    public List<AgileStory> findByIds(List<Long> ids) {
        List<AgileStory> agileStories = new ArrayList<>();
        ids.forEach(id -> agileStories.add(findById(id)));
        return agileStories;
    }

    @Override
    public List<AgileStory> findByNameContainsAndProjectId(String s, Long projectId) {
        return agileStoryRepository.findByNameContainsAndProjectIdAndAgileSprintNull(s, projectId);
    }

    @Override
    public List<AgileStory> findAll() {
        return agileStoryRepository.findAll();
    }

    @Override
    public List<AgileStory> findAllByProjectId(Long projectId) {
        return agileStoryRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<AgileStory> findAllBySprintId(Long sprintId) {
        return agileStoryRepository.findAllByAgileSprint_Id(sprintId);
    }


}

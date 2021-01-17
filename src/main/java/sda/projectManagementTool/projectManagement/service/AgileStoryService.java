package sda.projectManagementTool.projectManagement.service;

import sda.projectManagementTool.projectManagement.repository.model.AgileStory;
import sda.projectManagementTool.projectManagement.repository.model.AgileStoryStatus;

import java.util.List;

public interface AgileStoryService {

    AgileStory save(AgileStory agileStory);
    void delete(Long id);
    AgileStory findById(Long id);
    AgileStory assignStoryToUser(AgileStory agileStory, String username);
    AgileStory updateStatus(Long id, AgileStoryStatus newStatus);
    List<AgileStory> findByIds(List<Long> ids);
    List<AgileStory> findByNameContainsAndProjectId(String name, Long projectId);
    List<AgileStory> findAll();
    List<AgileStory> findAllByProjectId(Long projectId);
    List<AgileStory> findAllBySprintId(Long sprintId);
}

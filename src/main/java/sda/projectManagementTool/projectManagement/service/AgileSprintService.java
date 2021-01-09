package sda.projectManagementTool.projectManagement.service;

import sda.projectManagementTool.projectManagement.repository.model.AgileSprint;

import java.util.List;

public interface AgileSprintService {

    AgileSprint save(AgileSprint agileSprint);
    AgileSprint assignStoriesToSprint(Long agileStoryId, Long agileSprintId);
    List<AgileSprint> findAllSprintsByProjectId(Long projectId);
    List<AgileSprint> findAllCompletedSprintsByProjectId(Long projectId);
    List<AgileSprint> findSprintsByProjectIdWhichStartInFuture(Long projectId);
    void delete(Long id);
}

package sda.projectManagementTool.projectManagement.service;

import org.springframework.data.domain.Page;
import sda.projectManagementTool.projectManagement.dto.ProjectDtoRequest;
import sda.projectManagementTool.projectManagement.repository.model.Project;
import sda.projectManagementTool.projectManagement.repository.model.User;

import java.util.List;

public interface ProjectService {

    Project save(Project project);
    Project findByName(String name);
    Project findById(Long id);
    void delete(Long id);
    Project update(ProjectDtoRequest project, Long id);
    Project assignUserToProject(String username, String projectName);
    List<Project> findAll();
    void sendInviteLinkToUserForProjectAssignment(String username, String projectName);
    Page<Project> findAllPaginated(int size, int page);

}

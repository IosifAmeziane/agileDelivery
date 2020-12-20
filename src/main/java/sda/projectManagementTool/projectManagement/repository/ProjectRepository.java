package sda.projectManagementTool.projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.projectManagementTool.projectManagement.repository.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String name);

}

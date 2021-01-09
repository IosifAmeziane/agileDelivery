package sda.projectManagementTool.projectManagement.repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sda.projectManagementTool.projectManagement.repository.model.Project;


@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    Project findByName(String name);
}

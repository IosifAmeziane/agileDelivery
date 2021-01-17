package sda.projectManagementTool.projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.projectManagementTool.projectManagement.repository.model.AgileStory;

import java.util.List;

@Repository
public interface AgileStoryRepository extends JpaRepository<AgileStory, Long> {
    //TODO: make this paginated using PagingAndSortingRepository
    List<AgileStory> findByNameContainsAndProjectIdAndAgileSprintNull(String s, Long projectId);
    List<AgileStory> findAllByProjectId(Long projectId);
    List<AgileStory> findAllByAgileSprint_Id(Long sprintId);
}

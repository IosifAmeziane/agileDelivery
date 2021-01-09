package sda.projectManagementTool.projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sda.projectManagementTool.projectManagement.repository.model.AgileSprint;

import java.time.LocalDate;
import java.util.List;

public interface AgileSprintRepository extends JpaRepository<AgileSprint, Long> {

    @Query("SELECT agileSprint from AgileSprint agileSprint WHERE agileSprint.startDate <= :endDate and agileSprint.endDate >= :startDate and agileSprint.project.id = :projectId")
    List<AgileSprint> getAgileSprintsInIntervalAndWithProjectId(LocalDate startDate, LocalDate endDate, Long projectId);

    @Query("SELECT agileSprint from AgileSprint  agileSprint WHERE agileSprint.endDate <= :currentDate and agileSprint.project.id = :projectId")
    List<AgileSprint> getAgileSprintCompletedByProjectId(LocalDate currentDate, Long projectId);

    @Query("SELECT agileSprint from AgileSprint agileSprint WHERE agileSprint.startDate > :currentDate and agileSprint.project.id = :projectId")
    List<AgileSprint> getAgileSprintsNotStartedByProjectId(LocalDate currentDate, Long projectId);

    List<AgileSprint> findAgileSprintByProjectId(Long id);
}

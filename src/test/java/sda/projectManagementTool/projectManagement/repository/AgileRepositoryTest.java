package sda.projectManagementTool.projectManagement.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import sda.projectManagementTool.projectManagement.repository.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AgileRepositoryTest {

    @Autowired
    AgileSprintRepository agileSprintRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AgileStoryRepository agileStoryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testAgileSprintIsPersistedInDb() {
        User user1 = new User("username1", "email@gmail.com", "password");
        User dbUser1 = userRepository.save(user1);

        User user2 = new User("username2", "email2@gmail.com", "password");
        User dbUser2 = userRepository.save(user2);

        Project project = new Project();
        project.setName("New project");
        project.setDescription("New description");
        project.setAdministrator(dbUser1);

        List<User> assignedUsers = new ArrayList<>();
        assignedUsers.add(dbUser2);
        project.setAssignedUsers(assignedUsers);

        Project projectDb = projectRepository.save(project);

        AgileStory agileStory1 = new AgileStory();
        agileStory1.setName("AgileStory1");
        agileStory1.setAssignedUser(dbUser2);
        agileStory1.setStatus(AgileStoryStatus.IN_PROGRESS);
        agileStory1.setWeight(4);
        agileStory1.setStoryPoints(13);

        AgileStory agileStory2 = new AgileStory();
        agileStory2.setName("AgileStory2");
        agileStory2.setAssignedUser(dbUser2);
        agileStory2.setStatus(AgileStoryStatus.DONE);
        agileStory2.setWeight(2);
        agileStory2.setStoryPoints(5);

        AgileStory agileStory1Db = agileStoryRepository.save(agileStory1);
        AgileStory agileStory2Db = agileStoryRepository.save(agileStory2);

        AgileSprint agileSprint = new AgileSprint();
        agileSprint.setStartDate(LocalDate.now());
        agileSprint.setEndDate(LocalDate.now().plusDays(14));
        agileSprint.setProject(projectDb);
        List<AgileStory> agileStories = new ArrayList<>();
        agileStories.add(agileStory1Db);
        agileStories.add(agileStory2Db);
        agileSprint.setAgileStories(agileStories);

        AgileSprint agileSprintDb = agileSprintRepository.save(agileSprint);
        Assertions.assertNotNull(agileSprintDb);
    }

    @Test
    public void testAgileSprintAreOverlapping() {
        User user1 = new User("username1", "email@gmail.com", "password");
        User dbUser1 = userRepository.save(user1);

        User user2 = new User("username2", "email2@gmail.com", "password");
        User dbUser2 = userRepository.save(user2);

        Project project = new Project();
        project.setName("New project");
        project.setDescription("New description");
        project.setAdministrator(dbUser1);
        List<User> assignedUsers = new ArrayList<>();
        assignedUsers.add(dbUser2);
        project.setAssignedUsers(assignedUsers);

        Project projectDb = projectRepository.save(project);

        AgileStory agileStory1 = new AgileStory();
        agileStory1.setName("AgileStory1");
        agileStory1.setAssignedUser(dbUser2);
        agileStory1.setStatus(AgileStoryStatus.IN_PROGRESS);
        agileStory1.setWeight(4);
        agileStory1.setStoryPoints(13);

        AgileStory agileStory2 = new AgileStory();
        agileStory2.setName("AgileStory2");
        agileStory2.setAssignedUser(dbUser2);
        agileStory2.setStatus(AgileStoryStatus.DONE);
        agileStory2.setWeight(2);
        agileStory2.setStoryPoints(5);

        AgileStory agileStory1Db = agileStoryRepository.save(agileStory1);
        AgileStory agileStory2Db = agileStoryRepository.save(agileStory2);

        AgileSprint agileSprint = new AgileSprint();
        agileSprint.setStartDate(LocalDate.now());
        agileSprint.setEndDate(LocalDate.now().plusDays(14));
        agileSprint.setProject(projectDb);

        List<AgileStory> agileSprints = new ArrayList<>();
        agileSprints.add(agileStory1Db);
        agileSprints.add(agileStory2Db);
        agileSprint.setAgileStories(agileSprints);

        AgileSprint agileSprintDb = agileSprintRepository.save(agileSprint);

        AgileSprint agileSprintOverlapping = new AgileSprint();
        agileSprintOverlapping.setStartDate(LocalDate.now().plusDays(1));
        agileSprintOverlapping.setEndDate(LocalDate.now().plusDays(15));
        agileSprintOverlapping.setProject(projectDb);

        List<AgileStory> agileSprintStories2 = new ArrayList<>();
        agileSprintStories2.add(agileStory1Db);
        agileSprintStories2.add(agileStory2Db);
        agileSprintOverlapping.setAgileStories(agileSprintStories2);

        List<AgileSprint> overlappingAgileSprints = agileSprintRepository.getAgileSprintsInIntervalAndWithProjectId(agileSprintOverlapping.getStartDate(), agileSprintOverlapping.getEndDate(), agileSprintOverlapping.getProject().getId());

        Assertions.assertEquals(1, overlappingAgileSprints.size());
    }

    @Test
    public void testRetrievingOfCompletedSprints() {
        User user1 = new User("username1", "email@gmail.com", "password");
        User dbUser1 = userRepository.save(user1);

        User user2 = new User("username2", "email2@gmail.com", "password");
        User dbUser2 = userRepository.save(user2);

        Project project = new Project();
        project.setName("New project");
        project.setDescription("New description");
        project.setAdministrator(dbUser1);
        List<User> assignedUsers = new ArrayList<>();
        assignedUsers.add(dbUser2);
        project.setAssignedUsers(assignedUsers);

        Project projectDb = projectRepository.save(project);

        AgileStory agileStory1 = new AgileStory();
        agileStory1.setName("AgileStory1");
        agileStory1.setAssignedUser(dbUser2);
        agileStory1.setStatus(AgileStoryStatus.IN_PROGRESS);
        agileStory1.setWeight(4);
        agileStory1.setStoryPoints(13);

        AgileStory agileStory2 = new AgileStory();
        agileStory2.setName("AgileStory2");
        agileStory2.setAssignedUser(dbUser2);
        agileStory2.setStatus(AgileStoryStatus.DONE);
        agileStory2.setWeight(2);
        agileStory2.setStoryPoints(5);

        AgileStory agileStory1Db = agileStoryRepository.save(agileStory1);
        AgileStory agileStory2Db = agileStoryRepository.save(agileStory2);

        AgileSprint agileSprint = new AgileSprint();
        agileSprint.setStartDate(LocalDate.now().minusDays(15));
        agileSprint.setEndDate(LocalDate.now().minusDays(1));
        agileSprint.setProject(projectDb);

        List<AgileStory> agileStories = new ArrayList<>();
        agileStories.add(agileStory1Db);
        agileStories.add(agileStory2Db);
        agileSprint.setAgileStories(agileStories);


        agileSprintRepository.save(agileSprint);

        List<AgileSprint> completedSprints = agileSprintRepository.getAgileSprintCompletedByProjectId(LocalDate.now(), agileSprint.getProject().getId());

        Assertions.assertEquals(1, completedSprints.size());
    }

    @Test
    public void testRetrieveNotStartedAgileSprints() {
        User user1 = new User("username1", "email@gmail.com", "password");
        User dbUser1 = userRepository.save(user1);

        User user2 = new User("username2", "email2@gmail.com", "password");
        User dbUser2 = userRepository.save(user2);

        Project project = new Project();
        project.setName("New project");
        project.setDescription("New description");
        project.setAdministrator(dbUser1);
        List<User> assignedUsers = new ArrayList<>();
        assignedUsers.add(dbUser2);
        project.setAssignedUsers(assignedUsers);

        Project projectDb = projectRepository.save(project);

        AgileStory agileStory1 = new AgileStory();
        agileStory1.setName("AgileStory1");
        agileStory1.setAssignedUser(dbUser2);
        agileStory1.setStatus(AgileStoryStatus.IN_PROGRESS);
        agileStory1.setWeight(4);
        agileStory1.setStoryPoints(13);

        AgileStory agileStory2 = new AgileStory();
        agileStory2.setName("AgileStory2");
        agileStory2.setAssignedUser(dbUser2);
        agileStory2.setStatus(AgileStoryStatus.DONE);
        agileStory2.setWeight(2);
        agileStory2.setStoryPoints(5);

        AgileStory agileStory1Db = agileStoryRepository.save(agileStory1);
        AgileStory agileStory2Db = agileStoryRepository.save(agileStory2);

        AgileSprint agileSprint = new AgileSprint();
        agileSprint.setStartDate(LocalDate.now().plusDays(1));
        agileSprint.setEndDate(LocalDate.now().plusDays(15));
        agileSprint.setProject(projectDb);

        List<AgileStory> agileStories = new ArrayList<>();
        agileStories.add(agileStory1Db);
        agileStories.add(agileStory2Db);
        agileSprint.setAgileStories(agileStories);


        agileSprintRepository.save(agileSprint);

        List<AgileSprint> completedSprints = agileSprintRepository.getAgileSprintsNotStartedByProjectId(LocalDate.now(), agileSprint.getProject().getId());

        Assertions.assertEquals(1, completedSprints.size());
    }
}

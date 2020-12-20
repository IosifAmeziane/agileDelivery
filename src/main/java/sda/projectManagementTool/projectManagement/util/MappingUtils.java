package sda.projectManagementTool.projectManagement.util;

import sda.projectManagementTool.projectManagement.dto.MinimumUserInfoDto;
import sda.projectManagementTool.projectManagement.repository.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public static MinimumUserInfoDto mapUserToUserDetailsProjectDto(User user) {
        List<String> roles = user.getRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());
        return new MinimumUserInfoDto(user.getId(), user.getUsername(), user.getEmail(), roles);
    }

}

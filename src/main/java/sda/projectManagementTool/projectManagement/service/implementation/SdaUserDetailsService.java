package sda.projectManagementTool.projectManagement.service.implementation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sda.projectManagementTool.projectManagement.repository.model.Role;
import sda.projectManagementTool.projectManagement.repository.model.User;
import sda.projectManagementTool.projectManagement.service.UserService;
import sda.projectManagementTool.projectManagement.service.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// serviciul e folosit de spring security
// pentru a valida ca utilizatorul exista
@Service
public class SdaUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SdaUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);

        if (user != null) {
//TODO:        To be implemented.
//        if (!user.isEmailConfirmed()) {
//            throw new NotAuthorizedException("User has not confirmed his email");
//        }
//
            return createUserDetailFromUserForAuthentication(user, mapUserRolesToGrantedAuthorities(user.getRoles()));
        } else {
            throw new UserNotFoundException(String.format("User with username %s was not found", s));
        }
    }

    private List<GrantedAuthority> mapUserRolesToGrantedAuthorities(Set<Role> roleSet) {
        Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
        for (Role role : roleSet) {
            authoritySet.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(authoritySet);
    }

    private UserDetails createUserDetailFromUserForAuthentication(User user, List<GrantedAuthority> authoriesOfUser) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true,  authoriesOfUser);
    }
}

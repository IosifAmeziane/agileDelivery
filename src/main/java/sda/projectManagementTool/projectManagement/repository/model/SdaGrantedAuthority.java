package sda.projectManagementTool.projectManagement.repository.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "authorities")
public class SdaGrantedAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    private String authority;

    private String username;


    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

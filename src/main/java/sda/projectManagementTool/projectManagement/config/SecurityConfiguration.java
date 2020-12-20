package sda.projectManagementTool.projectManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sda.projectManagementTool.projectManagement.service.implementation.SdaUserDetailsService;

@Component
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private SdaUserDetailsService sdaUserDetailsService;


    public SecurityConfiguration(SdaUserDetailsService userDetailsService) {
        this.sdaUserDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(sdaUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/users").permitAll()
                .antMatchers("/hello-world-for-project-manager").hasAuthority("PROJECT_MANAGER")
                .antMatchers("/hello-world-for-developer").hasAuthority("DEVELOPER")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().csrf().disable();
    }
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

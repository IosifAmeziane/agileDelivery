package sda.projectManagementTool.projectManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sda.projectManagementTool.projectManagement.repository.model.UserType;
import sda.projectManagementTool.projectManagement.service.implementation.SdaUserDetailsService;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private SdaUserDetailsService sdaUserDetailsService;

    public SecurityConfiguration(SdaUserDetailsService userDetailsService) {
        this.sdaUserDetailsService  = userDetailsService;
    }

    // AuthenticationManagerBuilder - clasa care se ocupa cu partea
    // de autentificare in spring
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(sdaUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    // Aceasta metoda se ocupa de partea de autorizare
    // a resurselor
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Role si Authority sunt sinonime - reprezinta acelasi lucru
        // TODO: adaugare restrictii pentru endpoint-urile noi definite
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/projects/users").permitAll()
                .antMatchers("/hello-dev").hasAuthority(UserType.DEVELOPER.name()) // Securizam endpointul de hello-dev si-l facem accesibil doar pentru utilizatori cu role de DEVELOPER
                .anyRequest() // precizam ca request-urile anterior configurate trebuie sa fie autorizate (adica trebuie sa avem un user logat)
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**") // inseamna ca regulile de aici se aplica pe toate rutele din aplicatie
                        .allowedMethods("*") // metodele HTTP, * este placeholder care spune ca accepta orice
                        .allowedOrigins("*"); // originile de unde vin request-urile
            }
        };
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

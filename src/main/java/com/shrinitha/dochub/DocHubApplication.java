package com.shrinitha.dochub;

import com.shrinitha.dochub.model.Role;
import com.shrinitha.dochub.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DocHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocHubApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role("ADMIN"));
            }
            if (roleRepository.findByName("EDITOR").isEmpty()) {
                roleRepository.save(new Role("EDITOR"));
            }
            if (roleRepository.findByName("VIEWER").isEmpty()) {
                roleRepository.save(new Role("VIEWER"));
            }
        };
    }
}

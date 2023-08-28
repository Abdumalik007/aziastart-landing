package com.azia.landing;

import com.azia.landing.entity.Admin;
import com.azia.landing.entity.User;
import com.azia.landing.repository.AdminRepository;
import com.azia.landing.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LandingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AdminRepository adminRepository,
            PasswordEncoder encoder
    ) {
        return args -> {
            if(adminRepository.count() == 0) {
                Admin admin = Admin.builder()
                        .user(
                                User.builder()
                                        .email("admin@gmail.com")
                                        .password(encoder.encode("123456"))
                                        .role(Role.ADMIN).build()
                        )
                        .firstName("Admin")
                        .lastName("Adminov")
                        .build();
                adminRepository.save(admin);
            }
        };
    }
}

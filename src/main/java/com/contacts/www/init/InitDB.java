package com.contacts.www.init;

import com.contacts.www.model.entity.AdminEntity;
import com.contacts.www.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitDB implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public InitDB(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initAdmin();

    }

    private void initAdmin() {
        final List<AdminEntity> adminEntities = adminRepository.findAll();

        String username = "admin";
        String password = "1234";

        if(adminEntities.isEmpty()) {
            AdminEntity admin = new AdminEntity();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);
        }

        String green = "\u001B[32m";
        String red = "\u001B[31m";
        String resetColor = "\u001B[0m";

        System.out.printf("\n%s ADMIN INITIALIZED / USE THESE FOR LOGIN -> \n%s username: %s \n password: %s%s\n", red, green, username, password, resetColor);
    }
}

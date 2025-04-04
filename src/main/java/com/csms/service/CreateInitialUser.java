package com.csms.service;

import com.csms.model.Users;
import com.csms.repository.UserRepository;
import com.csms.utils.configProperties.AdminCreds;
import com.csms.utils.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateInitialUser implements CommandLineRunner {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private AdminCreds credentials;

    @Override
    public void run(String... args) throws Exception {
        Users user = userRepository.findByRole(Role.ADMIN).orElse(null);
        if(null == user){
            Users admin= new Users();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail(credentials.getEmail());
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode(credentials.getPassword()));
            userRepository.save(admin);
        }
    }
}


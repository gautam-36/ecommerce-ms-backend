package com.example.user_service.bootstraps;

import com.example.user_service.domain.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DataInitializer implements ApplicationRunner {

    private final static Logger log = Logger.getLogger(DataInitializer.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAdminUser();
    }

    private void createAdminUser() {
        Optional<User> existingUser = userRepository.findByEmail("admin@gmail.com");
        if (existingUser.isPresent()) {
            log.info("Admin User already exists");
            return;
        }
        log.info("Creating admin user");

        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(user);
    }

}
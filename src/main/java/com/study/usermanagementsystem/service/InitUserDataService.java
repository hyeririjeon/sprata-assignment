package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitUserDataService {

    private final InMemoryUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initMasterUser() {
        if (userRepository.findByLoginId("master").isEmpty()) {
            User master = User.createMaster("master", passwordEncoder.encode("1234"), "masterUser");
            userRepository.save(master);
        }
    }
}

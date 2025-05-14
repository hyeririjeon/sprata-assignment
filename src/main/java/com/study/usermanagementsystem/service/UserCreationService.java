package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreationService {

    private final InMemoryUserRepository inMemoryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(SignUpRequestDto requestDto) {

        validateDuplicateLoginId(requestDto.getUsername());

        User user = User.create(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname());

        return UserResponseDto.from(inMemoryUserRepository.save(user));

    }

    private void validateDuplicateLoginId(String loginId) {
        inMemoryUserRepository.findByLoginId(loginId)
                .ifPresent(user -> {
                    throw new UserException(UserStatusCode.USER_ALREADY_EXISTS);
                });
    }
}

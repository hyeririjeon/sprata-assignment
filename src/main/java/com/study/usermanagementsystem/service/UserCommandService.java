package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public UserResponseDto grantAdminRole(String username) {
        User user = inMemoryUserRepository.findByLoginId(username)
                .orElseThrow(() -> new UserException(UserStatusCode.USER_NOT_FOUND));

        user.grantAdminRole();
        return UserResponseDto.from(user);
    }
}

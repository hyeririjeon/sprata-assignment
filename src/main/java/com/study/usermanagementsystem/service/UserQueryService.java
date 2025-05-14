package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.AdminUserResponseDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public UserResponseDto getUser(String username) {

        User user = inMemoryUserRepository.findByLoginId(username)
                .orElseThrow(() -> new UserException(UserStatusCode.USER_NOT_FOUND));

        if(user.isBanned()) {
            throw new UserException(UserStatusCode.USER_IS_BANNED);
        }

        return UserResponseDto.from(user);
    }

    public List<AdminUserResponseDto> getUsers() {

        return inMemoryUserRepository.findAll().stream()
                .map(AdminUserResponseDto::from)
                .toList();

    }

    public AdminUserResponseDto banUser(String username) {

        User user = inMemoryUserRepository.findByLoginId(username)
                .orElseThrow(() -> new UserException(UserStatusCode.USER_NOT_FOUND));

        if(user.isBanned()) {
            throw new UserException(UserStatusCode.USER_ALREADY_BANNED);
        }

        user.ban();

        return AdminUserResponseDto.from(user);
    }
}

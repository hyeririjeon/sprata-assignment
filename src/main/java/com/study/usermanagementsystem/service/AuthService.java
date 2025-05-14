package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.jwt.JwtUtil;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.request.LoginRequestDto;
import com.study.usermanagementsystem.dto.response.LoginResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InMemoryUserRepository inMemoryUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto requestDto) {

        User user = inMemoryUserRepository.findByLoginId(requestDto.getUsername())
                .orElseThrow(() -> new UserException(UserStatusCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserException(UserStatusCode.INVALID_CREDENTIALS);
        }

        if (user.isBanned()) {
            throw new UserException(UserStatusCode.USER_IS_BANNED);
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        return LoginResponseDto.from(token.substring(7));

    }
}

package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.common.security.UserDetailsImpl;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.AdminUserResponseDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.service.UserCommandService;
import com.study.usermanagementsystem.service.UserCreationService;
import com.study.usermanagementsystem.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserCreationService userCreationService;
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid SignUpRequestDto requestDto) {
        UserResponseDto responseDto  = userCreationService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/admin/users/{username}/roles")
    public ResponseEntity<UserResponseDto> grantAdminRole(@PathVariable String username) {
        UserResponseDto responseDto = userCommandService.grantAdminRole(username);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = userQueryService.getUser(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<AdminUserResponseDto>> getUser() {
        List<AdminUserResponseDto> responseDto = userQueryService.getUsers();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAnyRole('MASTER', 'ADMIN')")
    @PatchMapping("/admin/users/{username}/ban")
    public ResponseEntity<AdminUserResponseDto> banUser(@PathVariable String username) {
        AdminUserResponseDto responseDto = userQueryService.banUser(username);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}

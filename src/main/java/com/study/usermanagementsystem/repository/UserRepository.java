package com.study.usermanagementsystem.repository;


import com.study.usermanagementsystem.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByLoginId(String loginId);
}

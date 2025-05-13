package com.study.usermanagementsystem.repository;

import com.study.usermanagementsystem.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public User save(User user) {
        long id = sequence.getAndIncrement();
        user.setId(id);
        users.put(id, user);

        return user;
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {

        return users.values().stream()
                .filter(user -> user.getLoginId().equals(loginId))
                .findFirst();
    }

}

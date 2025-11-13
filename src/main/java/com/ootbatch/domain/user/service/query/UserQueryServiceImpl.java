package com.ootbatch.domain.user.service.query;

import com.ootbatch.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public int countAllUsers() {

        return userRepository.countAllUsers();
    }

    @Override
    public int countByIsDeleted(Boolean isDeleted) {

        return userRepository.countByIsDeleted(isDeleted);
    }

    @Override
    public int countUsersRegisteredSince(LocalDateTime start, LocalDateTime end) {

        return userRepository.countUsersRegisteredSince(start, end);
    }
}

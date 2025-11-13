package com.ootbatch.domain.user.service.query;


import java.time.LocalDateTime;

public interface UserQueryService {

    int countAllUsers();

    int countByIsDeleted(Boolean isDeleted);

    int countUsersRegisteredSince(LocalDateTime start, LocalDateTime end);
}

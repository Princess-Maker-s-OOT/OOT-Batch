package com.ootbatch.domain.user.repository;

import com.ootbatch.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {

    // 전체 유저 수
    @Query("""
            SELECT count(u)
            FROM User u
            WHERE u.role = 'ROLE_USER'
            """)
    int countAllUsers();

    // 활성 or 비활성 유저 수
    @Query("""
            SELECT count(u)
            FROM User u
            WHERE u.role = 'ROLE_USER'
              AND u.isDeleted = :isDeleted
            """)
    int countByIsDeleted(Boolean isDeleted);

    // 신규 가입자 수 (기간별 집계)
    @Query("""
            SELECT count(u)
            FROM User u
            WHERE u.role = 'ROLE_USER'
              AND u.createdAt >= :start
              AND u.createdAt < :end
            """)
    int countUsersRegisteredSince(LocalDateTime start, LocalDateTime end);
}

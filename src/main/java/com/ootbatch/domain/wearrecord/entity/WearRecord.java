package com.ootbatch.domain.wearrecord.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.clothes.entity.Clothes;
import com.ootbatch.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "wear_records")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WearRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @Column(name = "worn_at", nullable = false)
    private LocalDateTime wornAt;
}
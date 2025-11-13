package com.ootbatch.domain.closetclotheslink.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.closet.entity.Closet;
import com.ootbatch.domain.clothes.entity.Clothes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "closet_clothes_links")
public class ClosetClothesLink extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closet_id", nullable = false)
    private Closet closet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;
}
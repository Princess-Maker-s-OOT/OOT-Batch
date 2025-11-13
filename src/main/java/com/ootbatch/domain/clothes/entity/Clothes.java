package com.ootbatch.domain.clothes.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.category.entity.Category;
import com.ootbatch.domain.closetclotheslink.entity.ClosetClothesLink;
import com.ootbatch.domain.clothes.enums.ClothesColor;
import com.ootbatch.domain.clothes.enums.ClothesSize;
import com.ootbatch.domain.clothesImage.entity.ClothesImage;
import com.ootbatch.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "clothes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ClothesSize clothesSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ClothesColor clothesColor;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(nullable = true)
    private LocalDateTime lastWornAt;

    @OneToMany(mappedBy = "clothes")
    @Where(clause = "is_deleted = false")
    private List<ClothesImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "clothes")
    private List<ClosetClothesLink> closetClothesLinks = new ArrayList<>();
}

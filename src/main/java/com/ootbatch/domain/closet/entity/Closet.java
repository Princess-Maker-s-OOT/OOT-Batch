package com.ootbatch.domain.closet.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.closetclotheslink.entity.ClosetClothesLink;
import com.ootbatch.domain.closetimage.entity.ClosetImage;
import com.ootbatch.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@Table(name = "closets")
public class Closet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255, nullable = true)
    private String description;

    // 공개 여부 (true: 공개, false: 비공개)
    @Column(nullable = false)
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "closet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ClosetImage closetImage;

    @OneToMany(mappedBy = "closet")
    private List<ClosetClothesLink> closetClothesLinks = new ArrayList<>();
}
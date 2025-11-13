package com.ootbatch.domain.salepost.entity;

import com.ootbatch.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sale_post_images")
public class SalePostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private Integer displayOrder; // 이미지 표시 순서 (조회 시 정렬 기준)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_post_id", nullable = false)
    private SalePost salePost;
}

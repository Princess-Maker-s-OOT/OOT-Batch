package com.ootbatch.domain.recommendation.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.clothes.entity.Clothes;
import com.ootbatch.domain.user.entity.User;
import com.ootcommon.recommendation.status.RecommendationStatus;
import com.ootcommon.recommendation.type.RecommendationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "recommendations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 추천의 종류 (기부 또는 판매)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationType type;

    // 추천이 발생한 이유 (예: '마지막 착용일 1년 초과')
    @Column(nullable = false)
    private String reason;

    // 추천 기록의 현재 상태 관리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationStatus status;

    @Builder
    private Recommendation(User user, Clothes clothes, RecommendationType type, String reason, RecommendationStatus status) {
        this.user = user;
        this.clothes = clothes;
        this.type = type;
        this.reason = reason;
        this.status = status;
    }
}
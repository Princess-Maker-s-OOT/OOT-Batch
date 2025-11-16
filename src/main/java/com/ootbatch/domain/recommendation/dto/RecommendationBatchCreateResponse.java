package com.ootbatch.domain.recommendation.dto;

import com.ootbatch.domain.recommendation.entity.Recommendation;
import com.ootbatch.domain.recommendation.status.RecommendationStatus;
import com.ootbatch.domain.recommendation.type.RecommendationType;
import lombok.AccessLevel;
import lombok.Builder;

/**
 * 배치 서버용 추천 생성 응답 DTO
 * 미저장 상태의 Recommendation 엔티티를 직렬화하기 위한 DTO입니다.
 * 배치 서버의 Writer에서 이 데이터를 받아 저장합니다.
 */
@Builder(access = AccessLevel.PRIVATE)
public record RecommendationBatchCreateResponse(

        Long userId,
        Long clothesId,
        RecommendationType type,
        String reason,
        RecommendationStatus status
) {
    /**
     * 미저장 상태의 Recommendation 엔티티로부터 DTO 생성
     * ID, createdAt, updatedAt은 저장 후에 설정되므로 포함하지 않음
     */
    public static RecommendationBatchCreateResponse from(Recommendation recommendation) {

        return RecommendationBatchCreateResponse.builder()
                .userId(recommendation.getUser().getId())
                .clothesId(recommendation.getClothes().getId())
                .type(recommendation.getType())
                .reason(recommendation.getReason())
                .status(recommendation.getStatus())
                .build();
    }
}

package com.ootbatch.domain.recommendation.dto;

import com.ootcommon.recommendation.dto.RecommendationBatchCreateResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RecommendationBatchResult(

        Long userId,
        List<RecommendationBatchCreateResponse> recommendations,
        boolean success,
        String errorMessage
) {
    /**
     * 성공 케이스 생성
     */
    public static RecommendationBatchResult success(Long userId, List<RecommendationBatchCreateResponse> recommendations) {

        return RecommendationBatchResult.builder()
                .userId(userId)
                .recommendations(recommendations)
                .success(true)
                .build();
    }

    /**
     * 실패 케이스 생성
     */
    public static RecommendationBatchResult failure(Long userId, String errorMessage) {

        return RecommendationBatchResult.builder()
                .userId(userId)
                .recommendations(List.of())
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }

    /**
     * 생성된 추천 개수 반환
     */
    public int getRecommendationCount() {

        return recommendations != null ? recommendations.size() : 0;
    }
}
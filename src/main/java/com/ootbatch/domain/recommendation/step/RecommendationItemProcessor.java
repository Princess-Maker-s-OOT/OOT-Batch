package com.ootbatch.domain.recommendation.step;

import com.ootbatch.domain.recommendation.client.RecommendationApiClient;
import com.ootbatch.domain.recommendation.dto.RecommendationBatchResult;
import com.ootcommon.recommendation.dto.RecommendationBatchCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 사용자 ID를 받아 메인 서버 Internal API를 호출하여 추천을 생성하는 Processor
 * 각 사용자에 대해:
 * 1. 메인 서버 Internal API 호출
 * 2. 추천 생성 결과(DTO)를 받아옴
 * 3. 성공/실패 결과를 RecommendationBatchResult로 반환
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationItemProcessor implements ItemProcessor<Long, RecommendationBatchResult> {

    private final RecommendationApiClient recommendationApiClient;

    @Override
    public RecommendationBatchResult process(Long userId) {
        try {
            log.debug("Processing recommendations for user: {}", userId);

            List<RecommendationBatchCreateResponse> recommendations = recommendationApiClient.createRecommendationsForUser(userId);

            log.debug("Generated {} recommendations for user: {}", recommendations.size(), userId);

            return RecommendationBatchResult.success(userId, recommendations);

        } catch (Exception e) {
            log.error("Failed to process recommendations for user: {}", userId, e);

            return RecommendationBatchResult.failure(userId, e.getMessage());
        }
    }
}
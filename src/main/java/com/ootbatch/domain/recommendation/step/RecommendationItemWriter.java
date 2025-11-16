package com.ootbatch.domain.recommendation.step;

import com.ootbatch.domain.clothes.entity.Clothes;
import com.ootbatch.domain.recommendation.dto.RecommendationBatchResult;
import com.ootbatch.domain.recommendation.entity.Recommendation;
import com.ootbatch.domain.recommendation.repository.RecommendationRepository;
import com.ootbatch.domain.user.entity.User;
import com.ootcommon.recommendation.dto.RecommendationBatchCreateResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 생성된 추천을 DB에 저장하는 Writer
 * API 응답 DTO를 엔티티로 변환하여 Chunk 단위로 배치 저장합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationItemWriter implements ItemWriter<RecommendationBatchResult> {

    private final RecommendationRepository recommendationRepository;
    private final EntityManager entityManager;

    @Override
    public void write(Chunk<? extends RecommendationBatchResult> chunk) {

        List<Recommendation> allRecommendations = chunk.getItems().stream()
                .filter(RecommendationBatchResult::success)
                .flatMap(result -> result.recommendations().stream())
                .map(this::convertToEntity)
                .toList();

        if (!allRecommendations.isEmpty()) {
            recommendationRepository.saveAll(allRecommendations);
            log.debug("Saved {} recommendations in chunk", allRecommendations.size());
        }

        // 실패 케이스 로깅
        chunk.getItems().stream()
                .filter(result -> !result.success())
                .forEach(result -> log.warn(
                        "Failed to generate recommendations for user {}: {}",
                        result.userId(),
                        result.errorMessage()
                ));
    }

    /**
     * DTO를 엔티티로 변환
     * EntityManager.getReference를 사용하여 프록시 객체를 생성하므로
     * 실제 User/Clothes 데이터를 로드하지 않고 FK만 설정
     */
    private Recommendation convertToEntity(RecommendationBatchCreateResponse dto) {
        User userRef = entityManager.getReference(User.class, dto.userId());
        Clothes clothesRef = entityManager.getReference(Clothes.class, dto.clothesId());

        return Recommendation.builder()
                .user(userRef)
                .clothes(clothesRef)
                .type(dto.type())
                .reason(dto.reason())
                .status(dto.status())
                .build();
    }
}
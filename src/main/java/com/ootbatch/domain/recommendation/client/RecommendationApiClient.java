package com.ootbatch.domain.recommendation.client;

import com.ootbatch.domain.recommendation.dto.RecommendationBatchCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * 메인 서버의 Internal API를 호출하는 클라이언트
 * 배치 서버에서 추천 생성 로직을 위임하기 위해 사용
 */
@Slf4j
@Component
public class RecommendationApiClient {

    private final RestTemplate restTemplate;
    private final String mainServerUrl;

    public RecommendationApiClient(
            RestTemplate restTemplate,
            @Value("${internal.api.main-server-url}") String mainServerUrl
    ) {
        this.restTemplate = restTemplate;
        this.mainServerUrl = mainServerUrl;
    }

    /**
     * 메인 서버에 사용자별 추천 생성 요청
     *
     * @param userId 대상 사용자 ID
     * @return 생성된 추천 DTO 리스트 (미저장 상태)
     */
    public List<RecommendationBatchCreateResponse> createRecommendationsForUser(Long userId) {
        String url = mainServerUrl + "/internal/recommendations/batch/users/" + userId;

        try {
            log.debug("Calling internal API for user {}: {}", userId, url);

            ResponseEntity<List<RecommendationBatchCreateResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<RecommendationBatchCreateResponse> recommendations = response.getBody();

            if (recommendations == null) {
                log.warn("Received null response from internal API for user {}", userId);
                return Collections.emptyList();
            }

            log.debug("Received {} recommendations from internal API for user {}", recommendations.size(), userId);
            return recommendations;

        } catch (RestClientException e) {
            log.error("Failed to call internal API for user {}: {}", userId, e.getMessage());
            throw new RuntimeException("Internal API call failed for user " + userId, e);
        }
    }
}

package com.ootbatch.domain.dashboard.step;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import com.ootcommon.dashboard.constant.DashboardUserCacheNames;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 사용자 대시보드 배치 Writer
 * - 배치에서는 캐시만 무효화(evict)
 * - API에서 @Cacheable로 lazy load하여 최신 데이터 보장
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardUserBatchWriter implements ItemWriter<DashboardUserStatPair> {

    private final CacheManager cacheManager;

    @Override
    public void write(@NonNull Chunk<? extends DashboardUserStatPair> items) {
        Cache summaryCache = cacheManager.getCache(DashboardUserCacheNames.SUMMARY);
        Cache wearCache = cacheManager.getCache(DashboardUserCacheNames.WEAR_STATISTICS);

        // null 체크 추가 (리뷰 반영)
        if (summaryCache == null || wearCache == null) {
            log.error("Cache 등록 오류: 캐시 이름 오타 또는 설정 누락");
            throw new IllegalStateException("필요 캐시가 등록되어 있지 않습니다");
        }

        for (DashboardUserStatPair result : items) {
            try {
                // 배치에서는 캐시만 무효화 (evict)
                summaryCache.evict(result.getUserId());

                String wearKey = result.getUserId() + "_" + LocalDate.now();
                wearCache.evict(wearKey);

                log.info("Writer: 캐시 무효화 완료 userId={} (다음 API 호출 시 재계산)",
                        result.getUserId());
            } catch (Exception e) {
                log.error("Writer: 캐시 무효화 실패 userId={}", result.getUserId(), e);
            }
        }
    }
}
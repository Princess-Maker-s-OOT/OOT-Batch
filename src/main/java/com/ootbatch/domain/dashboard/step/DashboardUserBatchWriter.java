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

@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardUserBatchWriter implements ItemWriter<DashboardUserStatPair> {

    private final CacheManager cacheManager;

    @Override
    public void write(@NonNull Chunk<? extends DashboardUserStatPair> items) {

        Cache summaryCache = cacheManager.getCache(DashboardUserCacheNames.SUMMARY);
        Cache wearCache = cacheManager.getCache(DashboardUserCacheNames.WEAR_STATISTICS);

        for (DashboardUserStatPair result : items) {

            try {
                // 배치에서는 캐시만 무효화 (evict)
                summaryCache.evict(result.getUserId());

                String wearKey = result.getUserId() + "_" + LocalDate.now();
                wearCache.evict(wearKey);

                log.info("Writer: 캐시 무효화 완료 userId={} (다음 API 호출 시 재계산)", result.getUserId());
            } catch (Exception e) {
                log.error("Writer: 캐시 무효화 실패 userId={}", result.getUserId(), e);
            }
        }
    }
}
package com.ootbatch.domain.dashboard.step;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import com.ootcommon.dashboard.constant.DashboardUserCacheNames;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class DashboardUserBatchWriter implements ItemWriter<DashboardUserStatPair> {

    private final CacheManager cacheManager;

    @Autowired
    public DashboardUserBatchWriter(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void write(@NonNull Chunk<? extends DashboardUserStatPair> items) {
        Cache summaryCache = cacheManager.getCache(DashboardUserCacheNames.SUMMARY);
        Cache wearCache = cacheManager.getCache(DashboardUserCacheNames.WEAR_STATISTICS);

        if (summaryCache == null || wearCache == null) {
            log.error("Cache 등록 오류: 캐시 이름 오타 또는 설정 누락");
            throw new IllegalStateException("필요 캐시가 등록되어 있지 않습니다");
        }

        for (DashboardUserStatPair result : items) {
            try {
                summaryCache.put(result.getUserId(), result.getSummary());
                wearCache.put(result.getUserId() + "_" + LocalDate.now(), result.getWearStats());
                log.info("Writer: 캐싱 성공 userId={}", result.getUserId());
            } catch (Exception e) {
                log.error("Writer: 캐싱 실패 userId={}, 예외={}", result.getUserId(), e.getMessage(), e);
            }
        }
    }
}
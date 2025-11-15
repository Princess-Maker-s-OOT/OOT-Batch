package com.ootbatch.domain.dashboard.listener;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DashboardUserBatchWriteListener implements ItemWriteListener<DashboardUserStatPair> {
    @Override
    public void beforeWrite(Chunk<? extends DashboardUserStatPair> items) { log.debug(">>> 통계 캐싱 Write 시작"); }
    @Override
    public void afterWrite(Chunk<? extends DashboardUserStatPair> items) { log.debug(">>> 통계 캐싱 Write 완료"); }
    @Override
    public void onWriteError(Exception exception, Chunk<? extends DashboardUserStatPair> items) {
        log.error(">>> 통계 캐싱 Write 에러", exception);
    }
}
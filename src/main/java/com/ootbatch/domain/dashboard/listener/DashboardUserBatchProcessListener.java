package com.ootbatch.domain.dashboard.listener;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DashboardUserBatchProcessListener implements ItemProcessListener<Long, DashboardUserStatPair> {
    @Override
    public void beforeProcess(Long item) { log.debug(">>> 통계 집계 전처리: userId={}", item); }
    @Override
    public void afterProcess(Long item, DashboardUserStatPair result) { log.debug(">>> 통계 집계 완료: userId={}", item); }
    @Override
    public void onProcessError(Long item, Exception e) { log.error(">>> 통계 집계 에러: userId={}, 예외={}", item, e); }
}

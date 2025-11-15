package com.ootbatch.domain.dashboard.step;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import com.ootbatch.domain.dashboard.service.query.user.DashboardUserQueryService;
import com.ootcommon.dashboard.response.DashboardUserSummaryResponse;
import com.ootcommon.dashboard.response.DashboardUserWearStatisticsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardUserBatchProcessor implements ItemProcessor<Long, DashboardUserStatPair> {
    private final DashboardUserQueryService dashboardUserQueryService;

    @Override
    public DashboardUserStatPair process(Long userId) {

        log.info("Processor: 통계 집계 시작 userId={}", userId);

        DashboardUserSummaryResponse summary = dashboardUserQueryService.getUserDashboardSummary(userId);
        DashboardUserWearStatisticsResponse wearStats = dashboardUserQueryService.getUserWearStatistics(userId, LocalDate.now());

        log.info("Processor: 통계 집계 완료 userId={}", userId);

        return new DashboardUserStatPair(userId, summary, wearStats);
    }
}
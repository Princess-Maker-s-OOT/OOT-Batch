package com.ootbatch.domain.dashboard.service.query.user;

import com.ootcommon.dashboard.response.DashboardUserSummaryResponse;
import com.ootcommon.dashboard.response.DashboardUserWearStatisticsResponse;

import java.time.LocalDate;

public interface DashboardUserQueryService {

    DashboardUserSummaryResponse getUserDashboardSummary(Long userId);

    DashboardUserWearStatisticsResponse getUserWearStatistics(Long userId, LocalDate baseDate);
}

package com.ootbatch.domain.dashboard.service.query.user;

import com.ootbatch.domain.dashboard.dto.response.DashboardUserSummaryResponse;
import com.ootbatch.domain.dashboard.dto.response.DashboardUserWearStatisticsResponse;

import java.time.LocalDate;

public interface DashboardUserQueryService {

    DashboardUserSummaryResponse getUserDashboardSummary(Long userId);

    DashboardUserWearStatisticsResponse getUserWearStatistics(Long userId, LocalDate baseDate);
}

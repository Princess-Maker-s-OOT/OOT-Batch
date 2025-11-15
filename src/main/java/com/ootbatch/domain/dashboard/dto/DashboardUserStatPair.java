package com.ootbatch.domain.dashboard.dto;

import com.ootcommon.dashboard.response.DashboardUserSummaryResponse;
import com.ootcommon.dashboard.response.DashboardUserWearStatisticsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardUserStatPair {
    private final Long userId;
    private final DashboardUserSummaryResponse summary;
    private final DashboardUserWearStatisticsResponse wearStats;
}

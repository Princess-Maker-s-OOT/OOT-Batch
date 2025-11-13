package com.ootbatch.domain.dashboard.service.query.admin;

import com.ootbatch.domain.dashboard.dto.response.AdminClothesStatisticsResponse;
import com.ootbatch.domain.dashboard.dto.response.AdminSalePostStatisticsResponse;
import com.ootbatch.domain.dashboard.dto.response.AdminTopCategoryStatisticsResponse;
import com.ootbatch.domain.dashboard.dto.response.AdminUserStatisticsResponse;

import java.time.LocalDate;

public interface DashboardAdminQueryService {

    AdminUserStatisticsResponse adminUserStatistics(LocalDate baseDate);

    AdminClothesStatisticsResponse adminClothesStatistics();

    AdminSalePostStatisticsResponse adminSalePostStatistics(LocalDate baseDate);

    AdminTopCategoryStatisticsResponse adminTopCategoryStatistics();
}

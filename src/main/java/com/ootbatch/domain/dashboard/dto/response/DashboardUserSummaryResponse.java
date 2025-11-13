package com.ootbatch.domain.dashboard.dto.response;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardUserSummaryResponse {

    private final int totalClothes; // 등록된 옷 전체 수
    private final List<CategoryStat> categoryStat; // 카테고리별 옷 개수 limit 10
}

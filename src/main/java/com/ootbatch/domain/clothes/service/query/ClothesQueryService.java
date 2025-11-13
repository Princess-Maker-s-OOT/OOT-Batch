package com.ootbatch.domain.clothes.service.query;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import com.ootbatch.domain.clothes.dto.response.ClothesColorCount;
import com.ootbatch.domain.clothes.dto.response.ClothesSizeCount;
import com.ootbatch.domain.wearrecord.dto.response.ClothesWearCount;
import com.ootbatch.domain.wearrecord.dto.response.NotWornOverPeriod;

import java.util.List;

public interface ClothesQueryService {

    int countClothesByIsDeletedFalse();

    // 최상위 카테고리 기준 옷 통계
    List<CategoryStat> countTopCategoryStats();

    // 색상 기준 옷 통계
    List<ClothesColorCount> clothesColorsCount();

    // 사이즈 기준 옷 통계
    List<ClothesSizeCount> clothesSizesCount();

    // 카테고리 인기 순위
    List<CategoryStat> findTopCategoryStats();

    // 사용자 기준 옷 통계
    int countAllClothesByUserIdAndIsDeletedFalse(Long userId);

    // 사용자 기준 카테고리 옷 통계
    List<CategoryStat> countUserTopCategoryStats(Long userId);

    // 자주 입지 않은 옷 (전체 기간)
    List<ClothesWearCount> leastWornClothes(Long userId);

    // 옷 미착용 기간
    List<NotWornOverPeriod> notWornOverPeriod(Long userId);
}
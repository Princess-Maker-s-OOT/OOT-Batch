package com.ootbatch.domain.clothes.repository;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import com.ootbatch.domain.clothes.dto.response.ClothesColorCount;
import com.ootbatch.domain.clothes.dto.response.ClothesSizeCount;
import com.ootbatch.domain.wearrecord.dto.response.ClothesWearCount;
import com.ootbatch.domain.wearrecord.dto.response.NotWornOverPeriod;

import java.util.List;

public interface ClothesCustomRepository {

    // 최상위 카테고리 기준 옷 통계
    List<CategoryStat> countTopCategoryStats();

    // 색상 기준 옷 통계
    List<ClothesColorCount> clothesColorsCount();

    // 사이즈 기준 옷 통계
    List<ClothesSizeCount> clothesSizesCount();

    List<CategoryStat> findTopCategoryStats();

    // 자주 입지 않은 옷 (전체 기간)
    List<ClothesWearCount> leastWornClothes(Long userId);

    // 옷 미착용 기간
    List<NotWornOverPeriod> notWornOverPeriod(Long userId);
}

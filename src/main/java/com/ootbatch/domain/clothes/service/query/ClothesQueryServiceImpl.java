package com.ootbatch.domain.clothes.service.query;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import com.ootbatch.domain.clothes.dto.response.ClothesColorCount;
import com.ootbatch.domain.clothes.dto.response.ClothesSizeCount;
import com.ootbatch.domain.clothes.repository.ClothesRepository;
import com.ootbatch.domain.wearrecord.dto.response.ClothesWearCount;
import com.ootbatch.domain.wearrecord.dto.response.NotWornOverPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClothesQueryServiceImpl implements ClothesQueryService {

    private final ClothesRepository clothesRepository;

    @Override
    public int countClothesByIsDeletedFalse() {

        return clothesRepository.countAllClothesByIsDeletedFalse();
    }

    @Override
    public List<CategoryStat> countTopCategoryStats() {

        return clothesRepository.countTopCategoryStats();
    }

    @Override
    public List<ClothesColorCount> clothesColorsCount() {

        return clothesRepository.clothesColorsCount();
    }

    @Override
    public List<ClothesSizeCount> clothesSizesCount() {

        return clothesRepository.clothesSizesCount();
    }

    @Override
    public List<CategoryStat> findTopCategoryStats() {

        return clothesRepository.findTopCategoryStats();
    }

    @Override
    public int countAllClothesByUserIdAndIsDeletedFalse(Long userId) {

        return clothesRepository.countAllClothesByUserIdAndIsDeletedFalse(userId);
    }

    @Override
    public List<CategoryStat> countUserTopCategoryStats(Long userId) {

        return clothesRepository.countUserTopCategoryStats(userId);
    }

    @Override
    public List<ClothesWearCount> leastWornClothes(Long userId) {

        return clothesRepository.leastWornClothes(userId);
    }

    @Override
    public List<NotWornOverPeriod> notWornOverPeriod(Long userId) {

        List<NotWornOverPeriod> result = clothesRepository.notWornOverPeriod(userId)
                .stream()
                .map(dto -> NotWornOverPeriod.builder()
                        .clothesId(dto.getClothesId())
                        .clothesDescription(dto.getClothesDescription())
                        .lastWornAt(dto.getLastWornAt())
                        .daysNotWorn(dto.getLastWornAt() == null
                                ? 0L
                                : ChronoUnit.DAYS.between(dto.getLastWornAt(), LocalDateTime.now()))
                        .build())
                .toList();

        return result;
    }
}
package com.ootbatch.domain.wearrecord.service.query;

import com.ootbatch.domain.wearrecord.repository.WearRecordRepository;
import com.ootcommon.wearrecord.response.ClothesWearCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WearRecordQueryServiceImpl implements WearRecordQueryService {

    private final WearRecordRepository wearRecordRepository;

    @Override
    public List<ClothesWearCount> wornThisWeek(Long userId, LocalDate baseDate) {

        return wearRecordRepository.wornThisWeek(userId, baseDate);
    }

    @Override
    public List<ClothesWearCount> topWornClothes(Long userId) {

        return wearRecordRepository.topWornClothes(userId);
    }
}

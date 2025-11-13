package com.ootbatch.domain.salepost.service.query;

import com.ootbatch.domain.salepost.dto.response.SaleStatusCount;
import com.ootbatch.domain.salepost.repository.SalePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalePostQueryServiceImpl implements SalePostQueryService {

    private final SalePostRepository salePostRepository;

    @Override
    public long countByIsDeletedFalse() {

        return salePostRepository.countByIsDeletedFalse();
    }

    @Override
    public List<SaleStatusCount> saleStatusCounts() {

        return salePostRepository.saleStatusCounts();
    }

    @Override
    public int countSalePostsRegisteredSince(LocalDateTime start, LocalDateTime end) {

        return salePostRepository.countSalePostsRegisteredSince(start, end);
    }
}

package com.ootbatch.domain.salepost.service.query;

import com.ootbatch.domain.salepost.dto.response.SaleStatusCount;

import java.time.LocalDateTime;
import java.util.List;

public interface SalePostQueryService {

    long countByIsDeletedFalse();

    List<SaleStatusCount> saleStatusCounts();

    int countSalePostsRegisteredSince(LocalDateTime start, LocalDateTime end);
}

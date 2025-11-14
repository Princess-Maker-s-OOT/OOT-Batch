package com.ootbatch.domain.salepost.repository;

import com.ootbatch.domain.salepost.entity.SalePost;
import com.ootcommon.salepost.response.SaleStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SalePostRepository extends JpaRepository<SalePost, Long> {

    // 삭제되지 않은 총 판매글 조회
    long countByIsDeletedFalse();

    // 상태별 판매글 조회
    @Query("""
            SELECT sp.status, count(sp)
            FROM SalePost sp
            WHERE sp.isDeleted = false
            GROUP BY sp.status
            """)
    List<SaleStatusCount> saleStatusCounts();

    // 신규 판매글 (기간별 집계)
    @Query("""
            SELECT count(sp)
            FROM SalePost sp
            WHERE sp.isDeleted = false
              AND sp.createdAt >= :start
              AND sp.createdAt < :end
            """)
    int countSalePostsRegisteredSince(LocalDateTime start, LocalDateTime end);
}

package com.ootbatch.domain.clothes.repository;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import com.ootbatch.domain.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long>, ClothesCustomRepository {

    // 삭제되지 않은 옷 카운터
    int countAllClothesByIsDeletedFalse();

    int countAllClothesByUserIdAndIsDeletedFalse(Long userId);

    @Query("""
            SELECT c.category.name, count(c)
            FROM Clothes c
            where c.user.id = :userId
            group by c.category.id, c.category.name
            order by count(c) desc, c.category.id
            limit 10
            """)
    List<CategoryStat> countUserTopCategoryStats(@Param("userId") Long userId);
}
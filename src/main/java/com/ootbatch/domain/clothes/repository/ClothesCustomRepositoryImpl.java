package com.ootbatch.domain.clothes.repository;

import com.ootbatch.domain.category.dto.response.CategoryStat;
import com.ootbatch.domain.category.dto.response.QCategoryStat;
import com.ootbatch.domain.category.entity.QCategory;
import com.ootbatch.domain.clothes.dto.response.ClothesColorCount;
import com.ootbatch.domain.clothes.dto.response.ClothesSizeCount;
import com.ootbatch.domain.clothes.dto.response.QClothesColorCount;
import com.ootbatch.domain.clothes.dto.response.QClothesSizeCount;
import com.ootbatch.domain.clothes.entity.QClothes;
import com.ootbatch.domain.wearrecord.dto.response.ClothesWearCount;
import com.ootbatch.domain.wearrecord.dto.response.NotWornOverPeriod;
import com.ootbatch.domain.wearrecord.dto.response.QClothesWearCount;
import com.ootbatch.domain.wearrecord.dto.response.QNotWornOverPeriod;
import com.ootbatch.domain.wearrecord.entity.QWearRecord;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClothesCustomRepositoryImpl implements ClothesCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QClothes clothes = QClothes.clothes;
    private final QCategory category = QCategory.category;
    private final QWearRecord wearRecord = QWearRecord.wearRecord;

    // 항상 삭제되지 않은 데이터만 조회하도록 구현
    private BooleanExpression isDeletedFalse() {

        return clothes.isDeleted.eq(false);
    }

    @Override
    public List<CategoryStat> countTopCategoryStats() {

        QCategory parent = new QCategory("parent");
        QCategory grandParent = new QCategory("grandParent");

        var rootName = grandParent.name
                .coalesce(parent.name)
                .coalesce(category.name);

        return jpaQueryFactory
                .select(new QCategoryStat(
                        rootName,
                        clothes.count()
                ))
                .from(clothes)
                .join(clothes.category, category)
                .leftJoin(category.parent, parent)
                .leftJoin(parent.parent, grandParent)
                .where(isDeletedFalse())
                .groupBy(rootName)
                .orderBy(clothes.count().desc(), rootName.asc()) // 2순위 PK 대신 그룹 컬럼을 기준으로 정렬
                .fetch();
    }

    @Override
    public List<ClothesColorCount> clothesColorsCount() {

        return jpaQueryFactory
                .select(new QClothesColorCount(
                        clothes.clothesColor,
                        clothes.count()
                ))
                .from(clothes)
                .where(isDeletedFalse())
                .groupBy(clothes.clothesColor)
                .orderBy(clothes.count().desc(), clothes.clothesColor.asc()) // 2순위 PK 대신 그룹 컬럼을 기준으로 정렬
                .fetch();
    }

    @Override
    public List<ClothesSizeCount> clothesSizesCount() {

        return jpaQueryFactory
                .select(new QClothesSizeCount(
                        clothes.clothesSize,
                        clothes.count()
                ))
                .from(clothes)
                .where(isDeletedFalse())
                .groupBy(clothes.clothesSize)
                .orderBy(clothes.count().desc(), clothes.clothesSize.asc()) // 2순위 PK 대신 그룹 컬럼을 기준으로 정렬
                .fetch();
    }

    @Override
    public List<CategoryStat> findTopCategoryStats() {

        return jpaQueryFactory
                .select(new QCategoryStat(
                        category.name,
                        clothes.count()
                ))
                .from(clothes)
                .join(clothes.category, category)
                .where(isDeletedFalse())
                .groupBy(category.id, category.name)
                .orderBy(clothes.count().desc(), category.id.asc()) // 2순위 PK 대신 그룹 컬럼을 기준으로 정렬
                .limit(10)
                .fetch();
    }

    @Override
    public List<ClothesWearCount> leastWornClothes(Long userId) {

        return jpaQueryFactory
                .select(new QClothesWearCount(
                        clothes.id,
                        clothes.description,
                        wearRecord.id.count()
                ))
                .from(clothes)
                .leftJoin(wearRecord)
                .on(wearRecord.clothes.id.eq(clothes.id)
                        .and(wearRecord.user.id.eq(userId)))
                .where(clothes.user.id.eq(userId))
                .groupBy(clothes.id, clothes.description)
                .orderBy(wearRecord.id.count().asc(), clothes.id.asc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<NotWornOverPeriod> notWornOverPeriod(Long userId) {

        return jpaQueryFactory
                .select(new QNotWornOverPeriod(
                        clothes.id,
                        clothes.description,
                        clothes.lastWornAt
                ))
                .from(clothes)
                .leftJoin(wearRecord)
                .on(wearRecord.clothes.id.eq(clothes.id)
                        .and(wearRecord.user.id.eq(userId)))
                .where(clothes.user.id.eq(userId))
                .groupBy(clothes.id, clothes.description)
                .orderBy(clothes.lastWornAt.asc().nullsFirst(), clothes.id.asc())
                .limit(10)
                .fetch();
    }
}
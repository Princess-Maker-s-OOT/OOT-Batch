package com.ootbatch.domain.wearrecord.repository;

import com.ootbatch.domain.wearrecord.entity.WearRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WearRecordRepository extends JpaRepository<WearRecord, Long>, WearCustomRepository {
}
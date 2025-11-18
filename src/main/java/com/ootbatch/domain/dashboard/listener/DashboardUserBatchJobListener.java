package com.ootbatch.domain.dashboard.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DashboardUserBatchJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info(">>> 대시보드 배치 JOB 시작: {} (Id: {}, 시작 시각: {})",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getJobInstance().getId(),
                jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        LocalDateTime startTime = jobExecution.getStartTime();
        LocalDateTime endTime = jobExecution.getEndTime();
        long durationMillis = java.time.Duration.between(startTime, endTime).toMillis();
        long hours = durationMillis / (1000 * 60 * 60);
        long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (durationMillis % (1000 * 60)) / 1000;

        String duration = hours > 0 ? String.format("%d시간 %d분", hours, minutes)
                : minutes > 0 ? String.format("%d분", minutes)
                : String.format("%d초", seconds);

        log.info(">>> 대시보드 배치 JOB 종료: 상태={}, 총 소요시간={}, 종료 시각={}",
                jobExecution.getStatus(), duration, endTime);

        // 모든 Step 결과 상세 로그
        long totalRead = 0, totalWrite = 0;

        for (StepExecution se : jobExecution.getStepExecutions()) {

            totalRead += se.getReadCount();
            totalWrite += se.getWriteCount();

            log.info("--> Step=[{}], 상태={}, [읽음={}, 기록={}, 처리누락={}, 커밋={}, 롤백={}, 스킵={}], 요약={}",
                    se.getStepName(), se.getStatus(),
                    se.getReadCount(), se.getWriteCount(), se.getProcessSkipCount(),
                    se.getCommitCount(), se.getRollbackCount(), se.getSkipCount(), se.getSummary());

            if (!se.getFailureExceptions().isEmpty()) {
                log.error("Step 장애 발생: {} - {}", se.getStepName(), se.getFailureExceptions());
            }
        }

        log.info(">>> 전체 Batch 요약: 읽음={}, 기록={}", totalRead, totalWrite);

        if (!jobExecution.getAllFailureExceptions().isEmpty()) {
            log.error(">>> 배치 전체 장애 발생: {}", jobExecution.getAllFailureExceptions());
            // 장애 알림/슬랙/메일 등 확장 포인트
        }
    }
}
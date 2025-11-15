package com.ootbatch.domain.dashboard.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DashboardUserBatchStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info(">>> STEP 시작: {} (Job={}, 시작 시각={})",
                stepExecution.getStepName(),
                stepExecution.getJobExecution().getJobInstance().getJobName(),
                stepExecution.getStartTime());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        LocalDateTime startTime = stepExecution.getStartTime();
        LocalDateTime endTime = stepExecution.getEndTime();
        long durationMillis = java.time.Duration.between(startTime, endTime).toMillis();
        long hours = durationMillis / (1000 * 60 * 60);
        long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (durationMillis % (1000 * 60)) / 1000;

        String duration = hours > 0 ? String.format("%d시간 %d분", hours, minutes)
                : minutes > 0 ? String.format("%d분", minutes)
                : String.format("%d초", seconds);

        log.info(">>> STEP 종료: {}, 상태={}, 읽음={}, 처리={}, 기록={}, 스킵={}, 소요시간={}",
                stepExecution.getStepName(), stepExecution.getStatus(),
                stepExecution.getReadCount(), stepExecution.getProcessSkipCount() + stepExecution.getWriteCount(),
                stepExecution.getWriteCount(), stepExecution.getSkipCount(), duration);

        log.info("요약: {}", stepExecution.getSummary());

        if (!stepExecution.getFailureExceptions().isEmpty()) {
            log.error("Step 장애 발생: {} 예외={}", stepExecution.getStepName(), stepExecution.getFailureExceptions());
        }

        if (stepExecution.getSkipCount() > 0) {
            log.warn(">>> Step 내 일부 아이템 스킵 발생");

            return new ExitStatus("COMPLETED WITH SKIPS");
        }

        return stepExecution.getExitStatus();
    }
}

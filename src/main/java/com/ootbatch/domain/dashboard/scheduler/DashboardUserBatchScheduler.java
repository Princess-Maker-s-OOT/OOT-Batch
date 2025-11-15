package com.ootbatch.domain.dashboard.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardUserBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job userDashboardBatchJob;

    // 매일 0시마다 실행 (여기선 분 단위 cron, 실제 원하는 주기대로 변경 가능)
    @Scheduled(cron = "0 * * * * *")
    public void runUserDashboardBatch() {
        log.info("사용자 대시보드 통계 집계 배치 시작");
        // 매 실행마다 고유 파라미터 추가 (예: 타임스탬프/UUID 등)
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(userDashboardBatchJob, params);
        } catch (Exception e) {
            log.error("사용자 배치 실행 실패", e);
        }
    }
}

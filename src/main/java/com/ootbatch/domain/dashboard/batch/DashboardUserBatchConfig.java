package com.ootbatch.domain.dashboard.batch;

import com.ootbatch.domain.dashboard.dto.DashboardUserStatPair;
import com.ootbatch.domain.dashboard.listener.DashboardUserBatchJobListener;
import com.ootbatch.domain.dashboard.listener.DashboardUserBatchStepListener;
import com.ootbatch.domain.dashboard.step.DashboardUserBatchProcessor;
import com.ootbatch.domain.dashboard.step.DashboardUserBatchWriter;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class DashboardUserBatchConfig {

    /**
     * Chunk 크기: 한 번에 처리할 사용자 수
     * 기존 스케줄러의 PAGE_SIZE와 동일하게 설정
     */
    private static final int CHUNK_SIZE = 100;
    /**
     * 재시도 최대 횟수
     */
    private static final int RETRY_LIMIT = 3;
    /**
     * Skip 최대 횟수
     */
    private static final int SKIP_LIMIT = 10;
    private final EntityManagerFactory entityManagerFactory;
    private final DashboardUserBatchProcessor processor;
    private final DashboardUserBatchWriter writer;
    private final DashboardUserBatchStepListener stepListener;
    private final DashboardUserBatchJobListener jobListener;

    public DashboardUserBatchConfig(
            EntityManagerFactory entityManagerFactory,
            DashboardUserBatchProcessor processor,
            DashboardUserBatchWriter writer,
            DashboardUserBatchStepListener stepListener,
            DashboardUserBatchJobListener jobListener
    ) {
        this.entityManagerFactory = entityManagerFactory;
        this.processor = processor;
        this.writer = writer;
        this.stepListener = stepListener;
        this.jobListener = jobListener;
    }

    @Bean
    public JpaPagingItemReader<Long> userIdItemReader() {
        return new JpaPagingItemReaderBuilder<Long>()
                .name("userIdItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u.id FROM User u WHERE u.isDeleted = false ORDER BY u.id")
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    public Step userDashboardBatchStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new org.springframework.batch.core.step.builder.StepBuilder("userDashboardBatchStep", jobRepository)
                .<Long, DashboardUserStatPair>chunk(CHUNK_SIZE, transactionManager)
                .reader(userIdItemReader())
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(RETRY_LIMIT)
                .skip(Exception.class)
                .skipLimit(SKIP_LIMIT)
                .build();
    }

    @Bean
    public Job userDashboardBatchJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new org.springframework.batch.core.job.builder.JobBuilder("userDashboardBatchJob", jobRepository)
                .listener(jobListener)
                .start(userDashboardBatchStep(jobRepository, transactionManager))
                .build();
    }
}

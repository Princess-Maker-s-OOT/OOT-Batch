package com.ootbatch.domain.dashboard.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DashboardUserBatchChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        log.info(">>> Chunk 시작");
    }
    @Override
    public void afterChunk(ChunkContext context) {
        log.info(">>> Chunk 종료");
    }
    @Override
    public void afterChunkError(ChunkContext context) {
        log.error(">>> Chunk 실행 중 에러 발생: {}", context.getStepContext().getStepExecution().getExitStatus());
    }
}
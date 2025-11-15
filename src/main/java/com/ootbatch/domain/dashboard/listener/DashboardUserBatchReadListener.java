package com.ootbatch.domain.dashboard.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DashboardUserBatchReadListener implements ItemReadListener<Long> {

    @Override
    public void beforeRead() { log.debug(">>> 사용자 아이디 Read 시작"); }

    @Override
    public void afterRead(Long item) { log.debug(">>> 사용자 아이디 Read 성공: {}", item); }

    @Override
    public void onReadError(Exception ex) { log.error(">>> 사용자 아이디 Read 에러", ex); }
}

package com.ootbatch.domain.recommendation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BatchErrorCode {

    BATCH_HISTORY_NOT_FOUND("BATCH_HISTORY_NOT_FOUND", HttpStatus.NOT_FOUND, "최근 배치 이력이 존재하지 않습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
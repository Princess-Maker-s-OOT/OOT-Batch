package com.ootbatch.domain.recommendation.exception;

import lombok.Getter;

@Getter
public class BatchException extends RuntimeException {

    private final BatchErrorCode errorCode;

    public BatchException(BatchErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

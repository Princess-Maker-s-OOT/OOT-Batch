package com.ootbatch.common.exception;

import org.springframework.http.HttpStatus;

public interface SuccessCode {

    String getCode();

    HttpStatus getHttpStatus();

    String getMessage();
}

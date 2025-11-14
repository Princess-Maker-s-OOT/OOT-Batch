package com.ootbatch.domain.image.exception;

import com.ootbatch.common.exception.GlobalException;

public class ImageException extends GlobalException {
    public ImageException(ImageErrorCode errorCode) {
        super(errorCode);
    }
}
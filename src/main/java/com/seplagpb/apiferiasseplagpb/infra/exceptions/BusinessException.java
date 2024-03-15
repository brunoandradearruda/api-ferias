package com.seplagpb.apiferiasseplagpb.infra.exceptions;

public class BusinessException extends  RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

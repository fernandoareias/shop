package com.fernando.produtos.api.dtos.commons;

public class BaseResponse<T> {
    private Integer statusCode;
    private String message;
    private T response;

    public BaseResponse(Integer statusCode, String message, T response) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }
}

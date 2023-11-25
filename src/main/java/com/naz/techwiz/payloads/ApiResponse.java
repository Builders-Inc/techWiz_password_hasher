package com.naz.techwiz.payloads;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private HttpStatus httpStatus;
    private int statusCode;
    private T data;
}

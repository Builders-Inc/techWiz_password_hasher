package com.naz.techwiz.exceptions;

import com.naz.techwiz.payloads.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class OurExceptionHandler {
    @ExceptionHandler(OurException.class)
    public ApiResponse<Map<String, Object>> handleException(OurException exception) {
        Map<String, Object> data = new HashMap<>();
        data.put("", exception);
        data.put("exception", exception.getClass());
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setData(data);
        response.setMessage(exception.getMessage());
        return response;
    }
}
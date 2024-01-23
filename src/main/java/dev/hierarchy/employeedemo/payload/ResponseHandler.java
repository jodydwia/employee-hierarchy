package dev.hierarchy.employeedemo.payload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(
            HttpStatus httpStatus, Object responseObject
    )
    {
        Map<String, Object> response = new HashMap<>();
        response.put("code", httpStatus.value());
        response.put("data", responseObject);

        return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatus.value()));
    }
}
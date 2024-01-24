package dev.hierarchy.employeedemo.service;

import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    ResponseEntity<Object> getEmployee(String name) throws IOException;

    List<EmployeeResponse> getAllEmployee() throws IOException;

}

package dev.hierarchy.employeedemo.service;

import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    Employee[] mapEmployeeJsonToObject() throws IOException;

    ResponseEntity<Object> searchEmployee(String name) throws IOException;

    List<EmployeeResponse> getEmployees() throws IOException;

}

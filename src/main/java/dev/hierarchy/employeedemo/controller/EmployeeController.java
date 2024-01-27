package dev.hierarchy.employeedemo.controller;

import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import dev.hierarchy.employeedemo.payload.ResponseHandler;
import dev.hierarchy.employeedemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchEmployee(@RequestParam(name = "name", required = false, defaultValue = "") String name) throws IOException {

        return employeeService.searchEmployee(name);
    }

    @GetMapping()
    public ResponseEntity<Object> getEmployees() throws IOException {

        List<EmployeeResponse> response = employeeService.getEmployees();

        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }
}

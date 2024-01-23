package dev.hierarchy.employeedemo.controller;

import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import dev.hierarchy.employeedemo.payload.ResponseHandler;
import dev.hierarchy.employeedemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getEmployee(@RequestParam(name = "name", required = false, defaultValue = "") String name
    ) throws IOException {

        TreeNode response = employeeService.getEmployee(name);

        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllEmployee() throws IOException {

        List<EmployeeResponse> response = employeeService.getAllEmployee();

        return ResponseHandler.responseBuilder(HttpStatus.OK, response);
    }
}

package dev.hierarchy.employeedemo.service;

import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    TreeNode getEmployee(String name) throws IOException;

    List<EmployeeResponse> getAllEmployee() throws IOException;

}

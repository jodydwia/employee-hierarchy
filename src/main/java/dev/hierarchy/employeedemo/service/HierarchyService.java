package dev.hierarchy.employeedemo.service;

import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.EmployeeHierarchyResponse;

public interface HierarchyService {

    EmployeeHierarchyResponse getHierarchy(TreeNode childEmployee, Employee[] employees, Employee employee);
}

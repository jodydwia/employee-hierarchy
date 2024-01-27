package dev.hierarchy.employeedemo.service;

import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;

public interface HierarchyService {

    TreeNode getHierarchy(TreeNode childEmployee, Employee[] employees, Employee employee);
}

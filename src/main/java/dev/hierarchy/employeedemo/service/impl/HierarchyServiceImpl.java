package dev.hierarchy.employeedemo.service.impl;

import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.service.HierarchyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class HierarchyServiceImpl implements HierarchyService {

    private TreeNode parentEmployee = null;

    @Override
    public TreeNode getHierarchy(TreeNode childEmployee, Employee[] employees, Employee employee) {

        if (employee.getManagerId() != null) {

            Employee manager = Arrays.stream(employees).filter(emp -> emp.getId().equals(employee.getManagerId())).toList().get(0);

            parentEmployee = new TreeNode(manager);

            parentEmployee.addChild(childEmployee);

            childEmployee = parentEmployee;

            return getHierarchy(childEmployee, employees, manager);
        } else {
            return parentEmployee;
        }
    }
}

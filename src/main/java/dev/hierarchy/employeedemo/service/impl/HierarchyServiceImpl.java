package dev.hierarchy.employeedemo.service.impl;

import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.EmployeeHierarchyResponse;
import dev.hierarchy.employeedemo.service.HierarchyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class HierarchyServiceImpl implements HierarchyService {
    EmployeeHierarchyResponse employeeHierarchy = new EmployeeHierarchyResponse();
    private int directReports = 0;
    private int reports = 0;

    @Override
    public EmployeeHierarchyResponse getHierarchy(TreeNode childEmployee, Employee[] employees, Employee employee) {

        if (employee.getManagerId() != null) {
            Employee manager = Arrays.stream(employees).filter(emp -> emp.getId().equals(employee.getManagerId())).toList().get(0);

            employeeHierarchy.setTreeNode(new TreeNode(manager));

            employeeHierarchy.getTreeNode().addChild(childEmployee);

            directReports = 1;

            reports++;

            childEmployee = employeeHierarchy.getTreeNode();

            return getHierarchy(childEmployee, employees, manager);
        } else {
            employeeHierarchy.setDirectReports(directReports);

            employeeHierarchy.setIndirectReports(reports - directReports);

            directReports = 0;

            reports = 0;

            return employeeHierarchy;
        }
    }

}

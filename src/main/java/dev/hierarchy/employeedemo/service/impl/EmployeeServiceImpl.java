package dev.hierarchy.employeedemo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.tree.Tree;
import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import dev.hierarchy.employeedemo.payload.EmployeeResponseTree;
import dev.hierarchy.employeedemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.support.NullValue;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${app.jsonPath}")
    private String jsonPath;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private Employee[] getEmployees() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Employee[] employees = mapper.readValue(new File(jsonPath), Employee[].class);

        return employees;
    }

    @Override
    public TreeNode getEmployee(String name) throws IOException {


        Employee[] employees = getEmployees();

        TreeNode rootTreeNode = new TreeNode(new Employee());

        if (name != null && !name.equals("")) {
            Employee employee = Arrays.stream(employees).filter(e -> e.getName().equals(name)).toList().get(0);
            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(employee);

             rootTreeNode = new TreeNode(employee);


             rootTreeNode = getManager(rootTreeNode, employeeList, employees, employee);

        }

        return rootTreeNode;
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() throws IOException {

        Employee[] employees = getEmployees();

        return Arrays.stream(modelMapper.map(employees, EmployeeResponse[].class)).toList();
    }

    private TreeNode getManager(TreeNode rootTreeNode, List<Employee> employeeList, Employee[] employees, Employee employee) {
        if (employee.getManagerId() != null) {
                Employee manager = Arrays.stream(employees).filter(emp -> emp.getId().equals(employee.getManagerId())).toList().get(0);
                employeeList.add(manager);

            List<TreeNode> employeeNodeList = new ArrayList<>();

                TreeNode managerNode = new TreeNode(manager);

                employeeNodeList.add(managerNode);

                if(rootTreeNode.getChildNodes().size() == 0) {
                    rootTreeNode.addChild(managerNode);
                } else {
                    rootTreeNode.getChildNodes().get(rootTreeNode.getChildNodes().size() - 1).setChildNodes(employeeNodeList);
                }

                return getManager(rootTreeNode, employeeList, employees, manager);
            } else {
                return rootTreeNode;
            }
    }
}

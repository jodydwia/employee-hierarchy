package dev.hierarchy.employeedemo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.ApiResponse;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import dev.hierarchy.employeedemo.payload.ResponseHandler;
import dev.hierarchy.employeedemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity<Object> getEmployee(String name) throws IOException {

        Employee[] employees = getEmployees();

        TreeNode rootTreeNode = new TreeNode(new Employee());

        if (name != null && !name.equals("")) {

            List<Employee> employeeFilterList = Arrays.stream(employees).filter(e -> e.getName().equals(name)).toList();

            if(employeeFilterList.size() > 0) {
                Employee employee = employeeFilterList.get(0);
                List<Employee> employeeList = new ArrayList<>();
                employeeList.add(employee);

                rootTreeNode = new TreeNode(employee);

                rootTreeNode = getManager(rootTreeNode, employeeList, employees, employee);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, new ApiResponse(Boolean.FALSE, "Employee with the name " +name+ " was not found"));
            }

        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, rootTreeNode);
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

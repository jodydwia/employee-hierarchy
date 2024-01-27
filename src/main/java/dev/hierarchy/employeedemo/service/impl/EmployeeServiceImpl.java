package dev.hierarchy.employeedemo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hierarchy.employeedemo.model.Employee;
import dev.hierarchy.employeedemo.model.TreeNode;
import dev.hierarchy.employeedemo.payload.ApiResponse;
import dev.hierarchy.employeedemo.payload.EmployeeResponse;
import dev.hierarchy.employeedemo.payload.ResponseHandler;
import dev.hierarchy.employeedemo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${app.jsonPath}")
    private String jsonPath;
    private final ModelMapper modelMapper;
    private TreeNode parentEmployee = null;

    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private Employee[] getEmployees() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(new ClassPathResource(jsonPath).getInputStream(), Employee[].class);
    }

    private TreeNode getManager(TreeNode childEmployee, Employee[] employees, Employee employee) {

        if (employee.getManagerId() != null) {

            Employee manager = Arrays.stream(employees).filter(emp -> emp.getId().equals(employee.getManagerId())).toList().get(0);

            parentEmployee = new TreeNode(manager);

            parentEmployee.addChild(childEmployee);

            childEmployee = parentEmployee;

            return getManager(childEmployee, employees, manager);
        } else {
            return parentEmployee;
        }
    }

    @Override
    public ResponseEntity<Object> getEmployee(String name) throws IOException {

        Employee[] employees = getEmployees();

        TreeNode employeeHierarchy = new TreeNode(new Employee());

        if (name != null && !name.equals("")) {

            List<Employee> employeeFilterList = Arrays.stream(employees).filter(e -> e.getName().equals(name)).toList();

            if(employeeFilterList.size() == 1) {

                Employee employee = employeeFilterList.get(0);

                employeeHierarchy = new TreeNode(employee);

                if(employee.getManagerId() != null) {

                    employeeHierarchy = getManager(employeeHierarchy, employees, employee);

                } else {

                    return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, new ApiResponse(Boolean.FALSE, "Unable to process employee hierarchy. "+name+" not having hierarchy"));
                }
            } else if(employeeFilterList.size() > 1) {

                List<String> multipleManagers = new ArrayList<>();

                for (Employee e : employeeFilterList) {

                    Employee employee = Arrays.stream(employees).filter(emp -> emp.getId().equals(e.getManagerId())).toList().get(0);

                    multipleManagers.add(employee.getName());
                }

                return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, new ApiResponse(Boolean.FALSE, "Unable to process employee tree. Linton has multiple managers: " + multipleManagers.toString().replace("[", "").replace("]", "")));
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, new ApiResponse(Boolean.FALSE, "Employee with the name " +name+ " was not found"));
            }

        }

        return ResponseHandler.responseBuilder(HttpStatus.OK, employeeHierarchy);
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() throws IOException {

        Employee[] employees = getEmployees();

        return Arrays.stream(modelMapper.map(employees, EmployeeResponse[].class)).toList();
    }
}

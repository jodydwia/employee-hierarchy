package dev.hierarchy.employeedemo.payload;

import dev.hierarchy.employeedemo.model.Employee;
import lombok.Data;

@Data
public class EmployeeResponseTree {

    private Integer id;
    private String name;
    private Employee manager;

}

package dev.hierarchy.employeedemo.payload;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Integer id;
    private String name;
    private Integer managerId;

}

package dev.hierarchy.employeedemo.payload;

import dev.hierarchy.employeedemo.model.TreeNode;
import lombok.Data;

@Data
public class EmployeeHierarchyResponse {

    private TreeNode treeNode;

    private int directReports;

    private int indirectReports;

}

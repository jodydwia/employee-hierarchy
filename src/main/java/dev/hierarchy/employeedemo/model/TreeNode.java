package dev.hierarchy.employeedemo.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class TreeNode {

    private Object value;
    private List<TreeNode> childNodes;

    public TreeNode(Object value) {
        this.value = value;
        this.childNodes = new LinkedList<>();
    }

    public void addChild(TreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public Object getValue() {
        return value;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }
}


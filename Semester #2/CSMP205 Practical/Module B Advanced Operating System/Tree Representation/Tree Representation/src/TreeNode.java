// Open Terminal at Tree Representation Folder
// javac -d bin ./src/*.java
//java -cp bin TreeBuilder

import java.io.*;
import java.util.*;

class TreeNode {
    String name;
    List<TreeNode> children;

    TreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    void addChild(TreeNode child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return name + (children.isEmpty() ? "" : children.toString());
    }
}
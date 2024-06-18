import java.io.*;
import java.util.*;

public class TreeBuilder {

    public static TreeNode readTreeFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = null;

        while ((line = reader.readLine()) != null) {
            int level = countLeadingSpaces(line) / 4;
            String nodeName = line.trim();
            TreeNode newNode = new TreeNode(nodeName);

            if (level == 0) {
                root = newNode;
            } else {
                while (stack.size() > level) {
                    stack.pop();
                }
                TreeNode parent = stack.peek();
                parent.addChild(newNode);
            }

            stack.push(newNode);
        }

        reader.close();
        return root;
    }

    private static int countLeadingSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            TreeNode root = readTreeFromFile(
                    ".\\Tree Representation\\Input File\\inputFile.txt");
            System.out.println("Tree structure: " + root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
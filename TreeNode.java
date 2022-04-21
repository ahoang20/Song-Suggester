import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

class TreeNode {
    String value;
    TreeNode left;
    TreeNode right;

    public TreeNode(String value) {
        this.value = value;
        right = null;
        left = null;
    }

    public TreeNode(String value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public boolean isLeaf() {
        if (this.left == null && this.right == null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveFile(BufferedWriter writer) throws IOException {

        if (this.isLeaf()) {
            writer.write(this.value);
        } else {
            writer.write(this.value);
        }
        writer.newLine();

        if (this.left != null) {
            this.left.saveFile(writer);
        } else {
            writer.write("leaf");
            writer.newLine();
        }

        if (this.right != null) {
            this.right.saveFile(writer);
        } else {
            writer.write("leaf");
            writer.newLine();
        }
    }

    static TreeNode fromPreorder(BufferedReader reader) throws IOException {
        String content = reader.readLine();

        if ("leaf".equals(content)  || content == null) {
            return null;
        }

        TreeNode node = new TreeNode(content);
        TreeNode left = fromPreorder(reader);
        TreeNode right = fromPreorder(reader);

        node.left = left;
        node.right = right;

        return node;
    }
}
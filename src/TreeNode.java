import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    T data;
    TreeNode<T> parent;
    String type;
    List<TreeNode<T>> children;

    public TreeNode(T data, String type) {
        this.data = data;
        this.type = type;
        this.children = new LinkedList<TreeNode<T>>();
        this.parent = null;

    }

    public TreeNode<T> addChild(T child, String type) {
        TreeNode<T> childNode = new TreeNode<T>(child, type);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        return null;
    }


    // other features ...

}
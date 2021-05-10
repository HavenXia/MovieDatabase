package bptree;

/**
 * This class is the implementation of the internal node.
 */
public class InternalNode implements INode {
    private int maxDegree;
    private int minDegree;
    private int degree;
    private InternalNode left;
    private InternalNode right;
    private Integer[] keys;
    private INode[] children;
    
    // newly added parent
    private InternalNode parent;
    

    public InternalNode(int m, Integer[] keys) {
        this.setMaxDegree(m);
        this.setMinDegree((int) Math.ceil(m / 2.0));
        this.setDegree(0);
        this.setKeys(keys);
        this.setChildren(new INode[this.getMaxDegree() + 1]);
    }

    public InternalNode(int m, Integer[] keys, INode[] nodes) {
        this.setMaxDegree(m);
        this.setMinDegree((int) Math.ceil(m / 2.0));
        this.setDegree(findNullNode(nodes));
        this.setKeys(keys);
        this.setChildren(nodes);
    }

    /**
     * Append the node to the end of children
     * @param node - Node to append
     */
    public void appendChildNode(INode node) {
        this.getChildren()[getDegree()] = node;
        // increment the degree 
        this.setDegree(this.getDegree() + 1);
    }

//    /**
//     * Prepend the node to the front of children
//     * @param node
//     */
//    public void prependChildNode(INode node) {
//        for (int i = degree - 1; i >= 0; i--) {
//            children[i + 1] = children[i];
//        }
//        this.children[0] = node;
//        // increment the degree
//        this.degree++;
//    }

    /**
     * Find the index of a child specified by "node"
     * @param node - Child node to find
     * @return index of the found child
     */
    public int findChildIndex(INode node) {
        for (int i = 0; i < getChildren().length; i++) {
            if (getChildren()[i] == node) {
                return i;
            }
        }
        // return -1 if not found
        return -1;
    }

    /**
     * Insert a child node into the specified position
     * @param node - Node to insert
     * @param pos - Position to insert
     */
    public void insertChildNode(INode node, int pos) {
        for (int i = getDegree() - 1; i >= pos; i--) {
            getChildren()[i + 1] = getChildren()[i];
        }
        this.getChildren()[pos] = node;
        // increment the degree
        this.setDegree(this.getDegree() + 1);
    }

    /**
     * Remove the node at specified index.
     * @param index - Index of node to remove
     */
    public void removeNode(int index) {
        this.getChildren()[index] = null;
        this.setDegree(this.getDegree() - 1);
    }

    /**
     * Check if this node is over full(one more child than the max degree)
     * @return boolean indicating whether the node is full
     */
    public boolean isFull() {
        return this.getDegree() == getMaxDegree() + 1;
    }

    /**
     * Search for the first null child node in the children
     * @param nodes - Array of children node
     * @return index of first null child node
     */
    public int findNullNode(INode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean isLeaf() {
        return false;
    }
    
    /**
     * Getters and setters
     */
    int getMaxDegree() {
        return maxDegree;
    }

    void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    INode[] getChildren() {
        return children;
    }

    void setChildren(INode[] children) {
        this.children = children;
    }

    int getMinDegree() {
        return minDegree;
    }

    void setMinDegree(int minDegree) {
        this.minDegree = minDegree;
    }

    int getDegree() {
        return degree;
    }

    void setDegree(int degree) {
        this.degree = degree;
    }

    InternalNode getLeft() {
        return left;
    }

    void setLeft(InternalNode left) {
        this.left = left;
    }

    InternalNode getRight() {
        return right;
    }

    void setRight(InternalNode right) {
        this.right = right;
    }

    Integer[] getKeys() {
        return keys;
    }

    void setKeys(Integer[] keys) {
        this.keys = keys;
    }

    InternalNode getParent() {
        return parent;
    }

    void setParent(InternalNode parent) {
        this.parent = parent;
    }

}
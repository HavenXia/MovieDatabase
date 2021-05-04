package bptree;

/**
 * This class is the internal node class
 *
 */
public class InternalNode extends Node implements INode{
    int maxDegree;
    int minDegree;
    int degree;
    InternalNode left;
    InternalNode right;
    Integer[] keys;
    Node[] children;

    public InternalNode(int m, Integer[] keys) {
        this.maxDegree = m;
        this.minDegree = (int) Math.ceil(m / 2.0);
        this.degree = 0;
        this.keys = keys;
        this.children = new Node[this.maxDegree + 1];
    }

    public InternalNode(int m, Integer[] keys, Node[] nodes) {
        this.maxDegree = m;
        this.minDegree = (int) Math.ceil(m / 2.0);
        this.degree = findNullNode(nodes);
        this.keys = keys;
        this.children = nodes;
    }

    /**
     * Append the node to the end of children
     * @param node
     */
    public void appendChildNode(Node node) {
        this.children[degree] = node;
        // increment the degree 
        this.degree++;
    }
    
    /**
     * Prepend the node to the front of children
     * @param node
     */
    public void prependChildNode(Node node) {
        for (int i = degree - 1; i >= 0; i--) {
            children[i + 1] = children[i];
        }
        this.children[0] = node;
        // increment the degree
        this.degree++;
    }

    /**
     * Find the index of a child specified by "node"
     * @param node
     * @return
     */
    public int findChildIndex(Node node) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == node) {
                return i;
            }
        }
        // return -1 if not found
        return -1;
    }

    /**
     * Insert a child node into the specified position
     * @param node
     * @param pos
     */
    public void insertChildNode(Node node, int pos) {
        for (int i = degree - 1; i >= pos; i--) {
            children[i + 1] = children[i];
        }
        this.children[pos] = node;
        // increment the degree
        this.degree++;
    }

    /**
     * Remove the key at specified index
     * @param index
     */
    public void removeKey(int index) {
        this.keys[index] = null;
    }

    /**
     * Remove the node at specified "index".
     * Then decrement the degree of this node by 1.
     * @param index
     */
    public void removeNode(int index) {
        this.children[index] = null;
        this.degree--;
    }

    /**
     * Remove the node specified by "node".
     * Then decrement the degree of this node by 1.
     * @param node
     */
    public void removeNode(Node node) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == node) {
                this.children[i] = null;
            }
        }
        this.degree--;
    }

    /**
     * Check if the number of children less than the minimum degree
     */
    public boolean isDeficient() {
        return this.degree < this.minDegree;
    }

    /**
     * Check if the number of children exceeds the minimum degree
     */
    public boolean isLendable() {
        return this.degree > this.minDegree;
    }

    /**
     * Check if the number of children is equal to the minimum degree
     */
    public boolean isMergeable() {
        return this.degree == this.minDegree;
    }

    /**
     * Check if this node is over full(one more child than the max degree)
     */
    public boolean isFull() {
        return this.degree == maxDegree + 1;
    }

    /**
     * Search for the first null child node in the children
     * @param nodes
     * @return
     */
    public int findNullNode(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) {
                return i;
            }
        }
        return -1;
    }

}
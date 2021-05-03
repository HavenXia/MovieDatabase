package bptree;

/**
 * This class is the internal node class
 *
 */
public class InternalNode extends Node implements INode{
    int maxDegree;
    int minDegree;
    int degree;
    InternalNode leftSibling;
    InternalNode rightSibling;
    Integer[] keys;
    Node[] childPointers;

    public InternalNode(int m, Integer[] keys) {
        this.maxDegree = m;
        this.minDegree = (int) Math.ceil(m / 2.0);
        this.degree = 0;
        this.keys = keys;
        this.childPointers = new Node[this.maxDegree + 1];
    }

    public InternalNode(int m, Integer[] keys, Node[] pointers) {
        this.maxDegree = m;
        this.minDegree = (int) Math.ceil(m / 2.0);
        this.degree = linearNullSearch(pointers);
        this.keys = keys;
        this.childPointers = pointers;
    }

    public void appendChildPointer(Node pointer) {
        this.childPointers[degree] = pointer;
        this.degree++;
    }

    public int findIndexOfPointer(Node pointer) {
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] == pointer) {
                return i;
            }
        }
        return -1;
    }

    public void insertChildPointer(Node pointer, int index) {
        for (int i = degree - 1; i >= index; i--) {
            childPointers[i + 1] = childPointers[i];
        }
        this.childPointers[index] = pointer;
        this.degree++;
    }

    public void prependChildPointer(Node pointer) {
        for (int i = degree - 1; i >= 0; i--) {
            childPointers[i + 1] = childPointers[i];
        }
        this.childPointers[0] = pointer;
        this.degree++;
    }

    public void removeKey(int index) {
        this.keys[index] = null;
    }

    public void removePointer(int index) {
        this.childPointers[index] = null;
        this.degree--;
    }

    public void removePointer(Node pointer) {
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] == pointer) {
                this.childPointers[i] = null;
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

    public int linearNullSearch(Node[] pointers) {
        for (int i = 0; i < pointers.length; i++) {
            if (pointers[i] == null) {
                return i;
            }
        }
        return -1;
    }

}
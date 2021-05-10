package bptree;

import java.util.Arrays;

/**
 * This class is the implementation of the leaf node.
 */
public class LeafNode implements INode {
    private int maxNumPairs;
    private int minNumPairs;
    private int numOfPairs;
    private LeafNode left;
    private LeafNode right;
    private Pair[] dict;
    
    // newly added parent
    private InternalNode parent;

    public LeafNode(int m, Pair pair) {
        this.setMaxNumPairs(m - 1);
        this.setMinNumPairs((int) (Math.ceil(m / 2) - 1));
        this.setDict(new Pair[m]);
        this.setNumOfPairs(0);
        this.insert(pair);
    }

    public LeafNode(int m, Pair[] dictPairs, InternalNode parent) {
        this.setMaxNumPairs(m - 1);
        this.setMinNumPairs((int) (Math.ceil(m / 2) - 1));
        this.setDict(dictPairs);
        this.setNumOfPairs(findNullNode(dictPairs));
        this.setParent(parent);
    }

    /**
     * Delete a node at specified index
     * @param index - Index of the node to delete
     */
    public void delete(int index) {
        this.getDict()[index] = null;
        setNumOfPairs(getNumOfPairs() - 1);
    }

    /**
     * Insert a pair into the leaf node
     * @param pair - Pair to insert
     * @return true if insert is successful, false if the node is full
     */
    public boolean insert(Pair pair) {
        if (this.isFull()) {
            return false;
        } else {
            this.getDict()[getNumOfPairs()] = pair;
            setNumOfPairs(getNumOfPairs() + 1);
            Arrays.sort(this.getDict(), 0, getNumOfPairs());
            return true;
        }
    }
    
    /**
     * Check if the number of pairs equal to the maximum pairs
     * @return true if equal, false if not equal
     */
    public boolean isFull() {
        return getNumOfPairs() == getMaxNumPairs();
    }

    /**
     * Search for the first null child node in the children
     * @param pairs - All key-value pairs in the leaf node
     * @return index of first null child node
     */
    public int findNullNode(Pair[] pairs) {
        for (int i = 0; i < pairs.length; i++) {
            if (pairs[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determine if a node is a leaf
     * @return true if node is a leaf
     */
    public boolean isLeaf() {
        return true;
    }

    /**
     * Getters and setters
     */

    public int getNumOfPairs() {
        return numOfPairs;
    }

    public void setNumOfPairs(int numPairs) {
        this.numOfPairs = numPairs;
    }
    public Pair[] getDict() {
        return dict;
    }

    int getMaxNumPairs() {
        return maxNumPairs;
    }

    void setMaxNumPairs(int maxNumPairs) {
        this.maxNumPairs = maxNumPairs;
    }

    int getMinNumPairs() {
        return minNumPairs;
    }

    void setMinNumPairs(int minNumPairs) {
        this.minNumPairs = minNumPairs;
    }

    LeafNode getLeft() {
        return left;
    }

    void setLeft(LeafNode left) {
        this.left = left;
    }

    LeafNode getRight() {
        return right;
    }

    void setRight(LeafNode right) {
        this.right = right;
    }

    void setDict(Pair[] dict) {
        this.dict = dict;
    }

    InternalNode getParent() {
        return parent;
    }

    void setParent(InternalNode parent) {
        this.parent = parent;
    }

   
    
    
    
}
package bptree;

import java.util.Arrays;

public class LeafNode implements INode{
    int maxNumPairs;
    int minNumPairs;
    int numOfPairs;
    LeafNode left;
    LeafNode right;
    Pair[] dict;
    
    // newly added parent
    InternalNode parent;

    public LeafNode(int m, Pair pair) {
        this.maxNumPairs = m - 1;
        this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
        this.dict = new Pair[m];
        this.numOfPairs = 0;
        this.insert(pair);
    }

    public LeafNode(int m, Pair[] dictPairs, InternalNode parent) {
        this.maxNumPairs = m - 1;
        this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
        this.dict = dictPairs;
        this.numOfPairs = findNullNode(dictPairs);
        this.parent = parent;
    }

    /**
     * Delete a node at specified index
     * @param index
     */
    public void delete(int index) {
        this.dict[index] = null;
        numOfPairs--;
    }

    /**
     * Insert a pair into the leaf node
     * @param pair
     * @return true if insert is successful, false if the node is full
     */
    public boolean insert(Pair pair) {
        if (this.isFull()) {
            return false;
        } else {
            this.dict[numOfPairs] = pair;
            numOfPairs++;
            Arrays.sort(this.dict, 0, numOfPairs);
            return true;
        }
    }

    /**
     * Check if the number of pairs less than the minimum pairs
     */
    public boolean isDeficient() {
        return numOfPairs < minNumPairs;
    }

    /**
     * Check if the number of pairs greater than the minimum pairs
     */
    public boolean isLendable() {
        return numOfPairs > minNumPairs;
    }

    /**
     * Check if the number of pairs equal to the minimum pairs
     */
    public boolean isMergeable() {
        return numOfPairs == minNumPairs;
    }
    
    /**
     * Check if the number of pairs equal to the maximum pairs
     */
    public boolean isFull() {
        return numOfPairs == maxNumPairs;
    }

    /**
     * Search for the first null child node in the children
     * @param pairs
     * @return
     */
    public int findNullNode(Pair[] pairs) {
        for (int i = 0; i < pairs.length; i++) {
            if (pairs[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isLeaf() {
        return true;
    }

    /**
     * Getters and setters
     */
    public int getMaxNumPairs() {
        return maxNumPairs;
    }

    public void setMaxNumPairs(int maxNumPairs) {
        this.maxNumPairs = maxNumPairs;
    }

    public int getMinNumPairs() {
        return minNumPairs;
    }

    public void setMinNumPairs(int minNumPairs) {
        this.minNumPairs = minNumPairs;
    }

    public int getNumOfPairs() {
        return numOfPairs;
    }

    public void setNumOfPairs(int numPairs) {
        this.numOfPairs = numPairs;
    }

    public LeafNode getLeftSibling() {
        return left;
    }

    public void setLeftSibling(LeafNode leftSibling) {
        this.left = leftSibling;
    }

    public LeafNode getRightSibling() {
        return right;
    }

    public void setRightSibling(LeafNode rightSibling) {
        this.right = rightSibling;
    }

    public Pair[] getDict() {
        return dict;
    }

    public void setDict(Pair[] dict) {
        this.dict = dict;
    }
    
    
    
}
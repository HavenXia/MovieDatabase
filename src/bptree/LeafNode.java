package bptree;

import java.util.Arrays;

public class LeafNode extends Node implements INode{
    int maxNumPairs;
    int minNumPairs;
    int numPairs;
    LeafNode leftSibling;
    LeafNode rightSibling;
    Pair[] dictionary;

    public LeafNode(int m, Pair dp) {
        this.maxNumPairs = m - 1;
        this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
        this.dictionary = new Pair[m];
        this.numPairs = 0;
        this.insert(dp);
    }

    public LeafNode(int m, Pair[] dps, InternalNode parent) {
        this.maxNumPairs = m - 1;
        this.minNumPairs = (int) (Math.ceil(m / 2) - 1);
        this.dictionary = dps;
        this.numPairs = linearNullSearch(dps);
        this.parent = parent;
    }

    public void delete(int index) {
        this.dictionary[index] = null;
        numPairs--;
    }

    public boolean insert(Pair dp) {
        if (this.isFull()) {
            return false;
        } else {
            this.dictionary[numPairs] = dp;
            numPairs++;
            Arrays.sort(this.dictionary, 0, numPairs);

            return true;
        }
    }

    public boolean isDeficient() {
        return numPairs < minNumPairs;
    }

    public boolean isLendable() {
        return numPairs > minNumPairs;
    }

    public boolean isMergeable() {
        return numPairs == minNumPairs;
    }
    
    public boolean isFull() {
        return numPairs == maxNumPairs;
    }

    public int linearNullSearch(Pair[] dps) {
        for (int i = 0; i < dps.length; i++) {
            if (dps[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
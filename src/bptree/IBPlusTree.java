package bptree;

import java.util.ArrayList;

public interface IBPlusTree {
    
    /**
     * Binary search program
     * @param dps
     * @param numPairs
     * @param t
     * @return 
     */
    public int binarySearch(Pair[] dps, int numPairs, int t);
    
    /**
     * Find the leaf node with recursion
     * @param key
     * @return the leafnode 
     */
    public LeafNode findLeafNode(int target);
    
    /**
     * Find the leaf node with internal node and key with recursion
     * @param node
     * @param key
     * @return
     */
    public LeafNode findLeafNode(InternalNode node, int key);

    /**
     * Finding the index of the pointer
     * @param pointers
     * @param node
     * @return
     */
    public int findIndexOfPointer(INode[] pointers, LeafNode node);
    
    /**
     * Get the mid pointer of each node based on the order 
     * of the BPlus Tree
     * @return the index of mid pointer
     */
    public int getMidpoint();
    
    /**
     * Balance the tree 
     * @param in the internal node
     */
    public void handleDeficiency(InternalNode in);
    
    /**
     * Check if the B+ tree is empty
     * @return
     */
    public boolean isEmpty();
    
    /**
     * Find the place to insert new node
     * @param pointers
     * @return index of the first null space 
     */
    public int findNullNode(INode[] nodes);
    
    /**
     * 
     * @param pointers
     * @param amount
     */
    public void shiftDown(INode[] nodes, int diff);
    
    /**
     * sort all pairs in the Pair array
     * @param dictionary array of pairs to be sorted
     */
    public void sortDict(Pair[] dict);
    
    /**
     * Split child pointers of the node into two arrays
     * @param in the Internal node to split
     * @param split the threshold to split the pointers
     * @return the array of new pointers
     */
    public INode[] splitChildNodes(InternalNode in, int pos);
    
    /**
     * split leaf node pairs into two arrays
     * @param ln the Leaf node to split
     * @param split the threshold to split the pointers
     * @return the array of split pairs
     */
    public Pair[] splitDict(LeafNode leaf, int pos);
    
    /**
     * split the internal node
     * @param in
     */
    public void splitInternalNode(InternalNode in);
    
    /**
     * split the array of keys
     * @param keys
     * @param split
     * @return
     */
    public Integer[] splitKeys(Integer[] keys, int pos);
    
    /**
     * insert new key value pair into the B+ Tree
     * @param key
     * @param value
     */
    public void insert(int key, int value);
    
    /**
     * search a key in the B+ Tree
     * @param key
     * @return the movie id
     */
    public int search(int key);
    
    /**
     * search with a key lowerBound and upperBound in the B+ tree
     * @param lowerBound
     * @param upperBound
     * @return list of movie ids
     */
    public ArrayList<Integer> search(int lowerBound, int upperBound);
    
    
}

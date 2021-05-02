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
    public int binarySearch(DictionaryPair[] dps, int numPairs, int t);
    
    /**
     * Find the leaf node
     * @param key
     * @return
     */
    public LeafNode findLeafNode(int key);
    
    /**
     * Find the leaf node
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
    public int findIndexOfPointer(Node[] pointers, LeafNode node);
    
    /**
     * Get the mid point
     * @return
     */
    public int getMidpoint();
    
    /**
     * Balance the tree
     * @param in
     */
    public void handleDeficiency(InternalNode in);
    
    /**
     * 
     * @return
     */
    public boolean isEmpty();
    
    /**
     * 
     * @param pointers
     * @return
     */
    public int linearNullSearch(Node[] pointers);
    
    /**
     * 
     * @param pointers
     * @param amount
     */
    public void shiftDown(Node[] pointers, int amount);
    
    /**
     * 
     * @param dictionary
     */
    public void sortDictionary(DictionaryPair[] dictionary);
    
    /**
     * 
     * @param in
     * @param split
     * @return
     */
    public Node[] splitChildPointers(InternalNode in, int split);
    
    /**
     * 
     * @param ln
     * @param split
     * @return
     */
    public DictionaryPair[] splitDictionary(LeafNode ln, int split);
    
    /**
     * 
     * @param in
     */
    public void splitInternalNode(InternalNode in);
    
    /**
     * 
     * @param keys
     * @param split
     * @return
     */
    public Integer[] splitKeys(Integer[] keys, int split);
    
    /**
     * 
     * @param key
     * @param value
     */
    public void insert(int key, double value);
    
    /**
     * 
     * @param key
     * @return
     */
    public Double search(int key);
    
    /**
     * 
     * @param lowerBound
     * @param upperBound
     * @return
     */
    public ArrayList<Double> search(int lowerBound, int upperBound);
    
    
}

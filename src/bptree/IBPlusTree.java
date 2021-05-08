package bptree;

import java.util.ArrayList;

public interface IBPlusTree {
    
    /**
     * Insert new key value pair into the B+ Tree
     * @param key - Key of pair to add
     * @param value -  Value of pair to add
     */
    public void insert(int key, int value);
    
    /**
     * Search a key in the B+ Tree
     * @param key - Key to search for
     * @return the movie id
     */
    public int search(int key);
    
    /**
     * Search with a key lowerBound and upperBound in the B+ tree
     * @param lowerBound - Lower bound of range query
     * @param upperBound - Upper bound of range query
     * @return list of movie ids
     */
    public ArrayList<Integer> search(int lowerBound, int upperBound);
    
    
}
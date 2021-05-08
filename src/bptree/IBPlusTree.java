package bptree;

import java.util.ArrayList;

public interface IBPlusTree {
    
    /**
     * Insert new key value pair into the B+ Tree
     * @param key
     * @param value
     */
    public void insert(int key, int value);
    
    /**
     * Search a key in the B+ Tree
     * @param key
     * @return the movie id
     */
    public int search(int key);
    
    /**
     * Search with a key lowerBound and upperBound in the B+ tree
     * @param lowerBound
     * @param upperBound
     * @return list of movie ids
     */
    public ArrayList<Integer> search(int lowerBound, int upperBound);
    
    
}
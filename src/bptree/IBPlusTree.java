package bptree;

import java.util.ArrayList;

public interface IBPlusTree {
    
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
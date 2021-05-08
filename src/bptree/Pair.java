package bptree;

/**
 * This is the class for key-value pair
 *
 */
public class Pair implements Comparable<Pair> {
    
    private int key;
    private int value;

    public Pair(int key, int value) {
        this.setKey(key);
        this.setValue(value);
    }
    
    // Compare with other Pair objects
    public int compareTo(Pair o) {
        if (getKey() == o.getKey()) {
            return 0;
        } else if (getKey() > o.getKey()) {
            return 1;
        } else {
            return -1;
        }
    }

    int getKey() {
        return key;
    }

    void setKey(int key) {
        this.key = key;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }
    
}
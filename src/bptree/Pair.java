package bptree;

/**
 * This is the class for key-value pair
 *
 */
public class Pair implements Comparable<Pair> {
    
    int key;
    int value;

    public Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }
    
    // Compare with other Pair objects
    public int compareTo(Pair o) {
        if (key == o.key) {
            return 0;
        } else if (key > o.key) {
            return 1;
        } else {
            return -1;
        }
    }

    // getters and setters for key and value
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
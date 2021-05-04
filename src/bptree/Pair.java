package bptree;

/**
 * This is the class for key-value pair
 *
 */
public class Pair implements Comparable<Pair> {
    
    int key;
    double value;

    public Pair(int key, double value) {
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
}
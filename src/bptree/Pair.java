package bptree;

// class for key-value pair
public class Pair implements Comparable<Pair> {
    int key;
    double value;

    public Pair(int key, double value) {
        this.key = key;
        this.value = value;
    }
    
    // compare with other Pair objects
    public int compareTo(Pair o) {
        if (key == o.key) {
            return 0;
        } else if (key > o.key) {
            return 1;
        } else {
            return -1;
        }
    }
    
}
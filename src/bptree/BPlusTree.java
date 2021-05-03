package bptree;

import java.util.*;

/**
 * 
 * This class is an implementation of B+ tree
 *
 */
public class BPlusTree implements IBPlusTree {
    int m;
    InternalNode root;
    LeafNode firstLeaf;

    // m is the order of the B+ tree
    public BPlusTree(int m) {
        this.m = m;
        this.root = null;
    }

    // Binary search for the target
    public int binarySearch(Pair[] dps, int numPairs, int t) {
        Comparator<Pair> c = new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                Integer a = Integer.valueOf(o1.key);
                Integer b = Integer.valueOf(o2.key);
                return a.compareTo(b);
            }
        };
        return Arrays.binarySearch(dps, 0, numPairs, new Pair(t, 0), c);
    }

    /*
     * Starter method for finding a target,
     * always calls this method first before proceeding with the helper one
     */
    public LeafNode findLeafNode(int target) {

        Integer[] keys = this.root.keys;
        int i;
        
        // iterate through the keys of this root until finds first key smaller than target
        for (i = 0; i < this.root.degree - 1; i++) {
            if (target < keys[i]) {
                break;
            }
        }

        Node child = this.root.childPointers[i];
        
        // check if reaches the LeafNode level. If yes, return with this child instance
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            // if not, calls findLeafNode helper method until we find it
            return findLeafNode((InternalNode) child, target);
        }
    }

    // Overloaded helper method for findLeftNode(int target) recursively
    public LeafNode findLeafNode(InternalNode node, int key) {

        Integer[] keys = node.keys;
        int i;
        
        // iterate through the keys of this node until finds first key smaller than target
        for (i = 0; i < node.degree - 1; i++) {
            if (key < keys[i]) {
                break;
            }
        }
        
        Node childNode = node.childPointers[i];
        
        // check if reaches the LeafNode level. If yes, return with this child instance
        if (childNode instanceof LeafNode) {
            return (LeafNode) childNode;
        } else {
            // if not, recursively calls this method until we find it
            return findLeafNode((InternalNode) node.childPointers[i], key);
        }
    }

    // Find the index of the pointer
    public int findIndexOfPointer(Node[] pointers, LeafNode node) {
        int i;
        for (i = 0; i < pointers.length; i++) {
            if (pointers[i] == node) {
                break;
            }
        }
        return i;
    }

    // Get the mid point of each node based on the order of current tree
    public int getMidpoint() {
        return (int) Math.ceil((this.m + 1) / 2.0) - 1;
    }

    // Balance the tree
    public void handleDeficiency(InternalNode in) {

        InternalNode sibling;
        InternalNode parent = in.parent;

        if (this.root == in) {
            for (int i = 0; i < in.childPointers.length; i++) {
                if (in.childPointers[i] != null) {
                    // if ith node is InternalNode
                    if (in.childPointers[i] instanceof InternalNode) {
                        this.root = (InternalNode) in.childPointers[i];
                        this.root.parent = null;
                    } else if (in.childPointers[i] instanceof LeafNode) {
                        // if ith node is LeafNode
                        this.root = null;
                    }
                }
            }
        }

        else if (in.leftSibling != null && in.leftSibling.isLendable()) {
            sibling = in.leftSibling;
        } else if (in.rightSibling != null && in.rightSibling.isLendable()) {
            sibling = in.rightSibling;

            int borrowedKey = sibling.keys[0];
            Node pointer = sibling.childPointers[0];

            in.keys[in.degree - 1] = parent.keys[0];
            in.childPointers[in.degree] = pointer;

            parent.keys[0] = borrowedKey;

            sibling.removePointer(0);
            Arrays.sort(sibling.keys);
            sibling.removePointer(0);
            shiftDown(in.childPointers, 1);
        } else if (in.leftSibling != null && in.leftSibling.isMergeable()) {

        } else if (in.rightSibling != null && in.rightSibling.isMergeable()) {
            sibling = in.rightSibling;
            sibling.keys[sibling.degree - 1] = parent.keys[parent.degree - 2];
            Arrays.sort(sibling.keys, 0, sibling.degree);
            parent.keys[parent.degree - 2] = null;

            for (int i = 0; i < in.childPointers.length; i++) {
                if (in.childPointers[i] != null) {
                    sibling.prependChildPointer(in.childPointers[i]);
                    in.childPointers[i].parent = sibling;
                    in.removePointer(i);
                }
            }

            parent.removePointer(in);

            sibling.leftSibling = in.leftSibling;
        }

        if (parent != null && parent.isDeficient()) {
            handleDeficiency(parent);
        }
    }

    // Check if the B+ Tree is empty
    public boolean isEmpty() {
        return firstLeaf == null;
    }

    // Find first null node in the pointers array
    public int linearNullSearch(Node[] pointers) {
        for (int i = 0; i < pointers.length; i++) {
            if (pointers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void shiftDown(Node[] pointers, int amount) {
        Node[] newPointers = new Node[this.m + 1];
        for (int i = amount; i < pointers.length; i++) {
            newPointers[i - amount] = pointers[i];
        }
        pointers = newPointers;
    }

    // sort all pairs
    public void sortDictionary(Pair[] dictionary) {
        Arrays.sort(dictionary, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o1.compareTo(o2);
            }
        });
    }

    // Split current node pointers into two parts
    // all pointers after the split is put in a new pointers array
    public Node[] splitChildPointers(InternalNode in, int split) {

        Node[] pointers = in.childPointers;
        Node[] halfPointers = new Node[this.m + 1];

        for (int i = split + 1; i < pointers.length; i++) {
            halfPointers[i - split - 1] = pointers[i];
            in.removePointer(i);
        }

        return halfPointers;
    }

    // Split leaf node pairs into two arrays
    public Pair[] splitDictionary(LeafNode ln, int split) {

        Pair[] dictionary = ln.dictionary;

        Pair[] halfDict = new Pair[this.m];

        for (int i = split; i < dictionary.length; i++) {
            halfDict[i - split] = dictionary[i];
            ln.delete(i);
        }

        return halfDict;
    }

    // Split the internal node
    public void splitInternalNode(InternalNode in) {

        InternalNode parent = in.parent;

        int midpoint = getMidpoint();
        int newParentKey = in.keys[midpoint];
        Integer[] halfKeys = splitKeys(in.keys, midpoint);
        Node[] halfPointers = splitChildPointers(in, midpoint);

        in.degree = linearNullSearch(in.childPointers);

        InternalNode sibling = new InternalNode(this.m, halfKeys, halfPointers);
        for (Node pointer : halfPointers) {
            if (pointer != null) {
                pointer.parent = sibling;
            }
        }

        sibling.rightSibling = in.rightSibling;
        if (sibling.rightSibling != null) {
            sibling.rightSibling.leftSibling = sibling;
        }
        in.rightSibling = sibling;
        sibling.leftSibling = in;

        if (parent == null) {

            Integer[] keys = new Integer[this.m];
            keys[0] = newParentKey;
            InternalNode newRoot = new InternalNode(this.m, keys);
            newRoot.appendChildPointer(in);
            newRoot.appendChildPointer(sibling);
            this.root = newRoot;

            in.parent = newRoot;
            sibling.parent = newRoot;

        } else {

            parent.keys[parent.degree - 1] = newParentKey;
            Arrays.sort(parent.keys, 0, parent.degree);

            int pointerIndex = parent.findIndexOfPointer(in) + 1;
            parent.insertChildPointer(sibling, pointerIndex);
            sibling.parent = parent;
        }
    }

    // Split the array of keys
    public Integer[] splitKeys(Integer[] keys, int split) {

        Integer[] halfKeys = new Integer[this.m];

        keys[split] = null;

        for (int i = split + 1; i < keys.length; i++) {
            halfKeys[i - split - 1] = keys[i];
            keys[i] = null;
        }

        return halfKeys;
    }

    // Insert new key value pair into the B+ Tree
    public void insert(int key, double value) {
        if (isEmpty()) {

            LeafNode ln = new LeafNode(this.m, new Pair(key, value));

            this.firstLeaf = ln;

        } else {
            LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);

            if (!ln.insert(new Pair(key, value))) {

                ln.dictionary[ln.numPairs] = new Pair(key, value);
                ln.numPairs++;
                sortDictionary(ln.dictionary);

                int midpoint = getMidpoint();
                Pair[] halfDict = splitDictionary(ln, midpoint);

                if (ln.parent == null) {

                    Integer[] parent_keys = new Integer[this.m];
                    parent_keys[0] = halfDict[0].key;
                    InternalNode parent = new InternalNode(this.m, parent_keys);
                    ln.parent = parent;
                    parent.appendChildPointer(ln);

                } else {
                    int newParentKey = halfDict[0].key;
                    ln.parent.keys[ln.parent.degree - 1] = newParentKey;
                    Arrays.sort(ln.parent.keys, 0, ln.parent.degree);
                }

                LeafNode newLeafNode = new LeafNode(this.m, halfDict, ln.parent);

                int pointerIndex = ln.parent.findIndexOfPointer(ln) + 1;
                ln.parent.insertChildPointer(newLeafNode, pointerIndex);

                newLeafNode.rightSibling = ln.rightSibling;
                if (newLeafNode.rightSibling != null) {
                    newLeafNode.rightSibling.leftSibling = newLeafNode;
                }
                ln.rightSibling = newLeafNode;
                newLeafNode.leftSibling = ln;

                if (this.root == null) {

                    this.root = ln.parent;

                } else {
                    InternalNode in = ln.parent;
                    while (in != null) {
                        if (in.isFull()) {
                            splitInternalNode(in);
                        } else {
                            break;
                        }
                        in = in.parent;
                    }
                }
            }
        }
    }

    // Search a key in the B+ Tree - exact match
    public Double search(int key) {

        // return null if is empty
        if (isEmpty()) {
            return null;
        }

        // find the leaf node of this key
        LeafNode leaf = (this.root == null) ? this.firstLeaf : findLeafNode(key);

        Pair[] pairs = leaf.getDictionary();
        // call binary search for the key in pairs
        int index = binarySearch(pairs, leaf.getNumPairs(), key);

        if (index < 0) {
            return null;
        } else {
            return pairs[index].value;
        }
    }

    // Search a key specified with lowerBound and upperBound in the B+ tree - range query
    public ArrayList<Double> search(int lowerBound, int upperBound) {

        ArrayList<Double> values = new ArrayList<Double>();

        LeafNode currNode = this.firstLeaf;
        while (currNode != null) {

            Pair dps[] = currNode.dictionary;
            for (Pair dp : dps) {

                if (dp == null) {
                    break;
                }

                if (lowerBound <= dp.key && dp.key <= upperBound) {
                    values.add(dp.value);
                }
            }
            currNode = currNode.rightSibling;

        }

        return values;
    }

    public static void main(String[] args) {
        BPlusTree bpt = null;
        bpt = new BPlusTree(3);
        bpt.insert(5, 33);
        bpt.insert(15, 21);
        bpt.insert(25, 31);
        bpt.insert(35, 41);
        bpt.insert(45, 10);

        if (bpt.search(25) != null) {
            System.out.println("Found");
        } else {
            System.out.println("Not Found");
        }
        
    }
}
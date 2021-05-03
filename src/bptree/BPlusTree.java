package bptree;

// Searching on a B+ tree in Java

import java.util.*;

public class BPlusTree implements IBPlusTree{
    int m;
    InternalNode root;
    LeafNode firstLeaf;
    
    // m is order of the BPlusTree
    public BPlusTree(int m) {
        this.m = m;
        this.root = null;
    }

    // Binary search the target
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

    // use recursion to find the leaf node
    public LeafNode findLeafNode(int key) {

        Integer[] keys = this.root.keys;
        int i;

        for (i = 0; i < this.root.degree - 1; i++) {
            if (key < keys[i]) {
                break;
            }
        }

        Node child = this.root.childPointers[i];
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            return findLeafNode((InternalNode) child, key);
        }
    }

    // overloaded helper method for findLeftNode
    public LeafNode findLeafNode(InternalNode node, int key) {

        Integer[] keys = node.keys;
        int i;

        for (i = 0; i < node.degree - 1; i++) {
            if (key < keys[i]) {
                break;
            }
        }
        Node childNode = node.childPointers[i];
        if (childNode instanceof LeafNode) {
            return (LeafNode) childNode;
        } else {
            return findLeafNode((InternalNode) node.childPointers[i], key);
        }
    }

    // Finding the index of the pointer
    public int findIndexOfPointer(Node[] pointers, LeafNode node) {
        int i;
        for (i = 0; i < pointers.length; i++) {
            if (pointers[i] == node) {
                break;
            }
        }
        return i;
    }

    // Get the mid point of each node based on the order of current Tree
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
                    if (in.childPointers[i] instanceof InternalNode) {
                        this.root = (InternalNode) in.childPointers[i];
                        this.root.parent = null;
                    } else if (in.childPointers[i] instanceof LeafNode) {
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

    // check if the B+ Tree is empty
    public boolean isEmpty() {
        return firstLeaf == null;
    }

    // find first null in the pointers array
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

    // split current node pointers into two parts
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

    // split leaf node pairs into two arra
    public Pair[] splitDictionary(LeafNode ln, int split) {

        Pair[] dictionary = ln.dictionary;

        Pair[] halfDict = new Pair[this.m];

        for (int i = split; i < dictionary.length; i++) {
            halfDict[i - split] = dictionary[i];
            ln.delete(i);
        }

        return halfDict;
    }
    
    // split the internal node
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

    // split the array of keys
    public Integer[] splitKeys(Integer[] keys, int split) {

        Integer[] halfKeys = new Integer[this.m];

        keys[split] = null;

        for (int i = split + 1; i < keys.length; i++) {
            halfKeys[i - split - 1] = keys[i];
            keys[i] = null;
        }

        return halfKeys;
    }

    // insert new key value pair into the B+ Tree
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

    // search a key in the B+ Tree
    public Double search(int key) {

        if (isEmpty()) {
            return null;
        }

        LeafNode ln = (this.root == null) ? this.firstLeaf : findLeafNode(key);

        Pair[] dps = ln.dictionary;
        int index = binarySearch(dps, ln.numPairs, key);

        if (index < 0) {
            return null;
        } else {
            return dps[index].value;
        }
    }

    // search with a key lowerBound and upperBound in the B+ tree
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
        ;
    }
}
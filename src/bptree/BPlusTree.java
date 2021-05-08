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

    /**
     * Binary search for the target
     * @param dps
     * @param numPairs
     * @param t
     * @return 
     */
    private int binarySearch(Pair[] dps, int numPairs, int t) {
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

    /**
     * Find the leaf node with recursion
     * Starter method for finding a target,
     * always calls this method first before proceeding with the helper one
     * @param target
     * @return the LeafNode
     */
    private LeafNode findLeafNode(int target) {

        Integer[] keys = this.root.keys;
        int i;
        
        // iterate through the keys of this root until finds first key smaller than target
        for (i = 0; i < this.root.degree - 1; i++) {
            if (target < keys[i]) {
                break;
            }
        }

        INode child =  this.root.children[i];
        
        // check if reaches the LeafNode level. If yes, return with this child instance
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            // if not, calls findLeafNode helper method until we find it
            return findLeafNode((InternalNode) child, target);
        }
    }

    /**
     * Overloaded helper method for findLeftNode(int target) recursively
     * Find the leaf node with internal node and key with recursion
     * @param node
     * @param key
     * @return
     */
    private LeafNode findLeafNode(InternalNode node, int key) {

        Integer[] keys = node.keys;
        int i;
        
        // iterate through the keys of this node until finds first key smaller than target
        for (i = 0; i < node.degree - 1; i++) {
            if (key < keys[i]) {
                break;
            }
        }
        
        INode childNode = node.children[i];
        
        // check if reaches the LeafNode level. If yes, return with this child instance
        if (childNode instanceof LeafNode) {
            return (LeafNode) childNode;
        } else {
            // if not, recursively calls this method until we find it
            return findLeafNode((InternalNode) node.children[i], key);
        }
    }

//    /**
//     * Finding the index of the pointer
//     * @param pointers
//     * @param node
//     * @return
//     */
//    private int findIndexOfPointer(INode[] pointers, LeafNode node) {
//        int i;
//        for (i = 0; i < pointers.length; i++) {
//            if (pointers[i] == node) {
//                break;
//            }
//        }
//        return i;
//    }

    /**
     * Get the mid pointer of each node based on the order 
     * of the BPlus Tree
     * @return the index of mid pointer
     */
    private int getMidpoint() {
        return (int) Math.ceil((this.m + 1) / 2.0) - 1;
    }

//    // Balance the tree
//    public void handleDeficiency(InternalNode in) {
//
//        //InternalNode in = (InternalNode)inode;
//        
//        InternalNode sibling;
//        InternalNode parent = in.parent;
//        
//        
//
//        if (this.root == in) {
//            for (int i = 0; i < in.children.length; i++) {
//                if (in.children[i] != null) {
//                    // if ith node is InternalNode
//                    if (in.children[i] instanceof InternalNode) {
//                        this.root = (InternalNode) in.children[i];
//                        this.root.parent = null;
//                    } else if (in.children[i] instanceof LeafNode) {
//                        // if ith node is LeafNode
//                        this.root = null;
//                    }
//                }
//            }
//        }
//
//        else if (in.left != null && in.left.isLendable()) {
//            sibling = in.left;
//        } else if (in.right != null && in.right.isLendable()) {
//            sibling = in.right;
//
//            int borrowedKey = sibling.keys[0];
//            INode pointer = sibling.children[0];
//
//            in.keys[in.degree - 1] = parent.keys[0];
//            in.children[in.degree] = pointer;
//
//            parent.keys[0] = borrowedKey;
//
//            sibling.removeNode(0);
//            Arrays.sort(sibling.keys);
//            sibling.removeNode(0);
//            shiftDown(in.children, 1);
//        } else if (in.left != null && in.left.isMergeable()) {
//
//        } else if (in.right != null && in.right.isMergeable()) {
//            sibling = in.right;
//            sibling.keys[sibling.degree - 1] = parent.keys[parent.degree - 2];
//            Arrays.sort(sibling.keys, 0, sibling.degree);
//            parent.keys[parent.degree - 2] = null;
//
//            for (int i = 0; i < in.children.length; i++) {
//                if (in.children[i] != null) {
//                    sibling.prependChildNode(in.children[i]);
//                    ((InternalNode)in.children[i]).parent = sibling;
//                    in.removeNode(i);
//                }
//            }
//
//            parent.removeNode(in);
//
//            sibling.left = in.left;
//        }
//
//        if (parent != null && parent.isDeficient()) {
//            handleDeficiency(parent);
//        }
//    }

    /**
     * Check if the B+ tree is empty
     * @return
     */
    private boolean isEmpty() {
        return firstLeaf == null;
    }

    /**
     * Find the place to insert new node
     * Find first null node in the pointers array
     * @param pointers
     * @return index of the first null space 
     */
    private int findNullNode(INode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) {
                return i;
            }
        }
        return -1;
    }

//    /**
//     * Shift down the indexes of this nodes[] by deviation "diff"
//     */
//    public void shiftDown(INode[] nodes, int diff) {
//        INode[] newPointers = new INode[this.m + 1];
//        for (int i = diff; i < nodes.length; i++) {
//            newPointers[i - diff] = nodes[i];
//        }
//        nodes = newPointers;
//    }

    /**
     * Sort all pairs in the Pair array
     * @param dictionary array of pairs to be sorted
     */
    private void sortDict(Pair[] dict) {
        Arrays.sort(dict, new Comparator<Pair>() {
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

    /**
     * Split child pointers of the node into two arrays,
     * all pointers after the split is put in a new pointers array
     * @param in the Internal node to split
     * @param pos the threshold to split the pointers
     * @return the array of new pointers
     */
    private INode[] splitChildNodes(InternalNode in, int pos) {

        INode[] pointers = in.children;
        INode[] halfPointers = new INode[this.m + 1];

        for (int i = pos + 1; i < pointers.length; i++) {
            halfPointers[i - pos - 1] = pointers[i];
            // call helper function to remove the node in childPointers[]
            in.removeNode(i);
        }

        return halfPointers;
    }
    
    /**
     * Split the leaf node pairs into two arrays,
     * starting from the index specified by pos.
     * @param ln the Leaf node to split
     * @param pos the threshold to split the pointers
     * @return the array of split pairs
     */
    private Pair[] splitDict(LeafNode leaf, int pos) {

        Pair[] dictPairs = leaf.getDict();
        Pair[] halfDict = new Pair[this.m];

        // Split the leaf node pairs into two arrays from "cut"
        for (int i = pos; i < dictPairs.length; i++) {
            halfDict[i - pos] = dictPairs[i];
            leaf.delete(i);
        }

        return halfDict;
    }

    /**
     * Split the internal node
     * @param in
     */
    private void splitInternalNode(InternalNode in) {

        InternalNode parent = in.parent;

        int midpoint = getMidpoint();
        int newParentKey = in.keys[midpoint];
        Integer[] halfKeys = splitKeys(in.keys, midpoint);
        INode[] halfPointers = splitChildNodes(in, midpoint);

        in.degree = findNullNode(in.children);

        InternalNode sibling = new InternalNode(this.m, halfKeys, halfPointers);
        for (INode pointer : halfPointers) {
            if (pointer != null) {
                if (pointer instanceof LeafNode) {
                    ((LeafNode)pointer).parent = sibling;
                } else {
                    ((InternalNode)pointer).parent = sibling;
                }
                
                
            }
        }
        
        sibling.right = in.right;
        if (sibling.right != null) {
            sibling.right.left = sibling;
        }
        in.right = sibling;
        sibling.left = in;

        if (parent == null) {
            Integer[] keys = new Integer[this.m];
            keys[0] = newParentKey;
            InternalNode newRoot = new InternalNode(this.m, keys);
            newRoot.appendChildNode(in);
            newRoot.appendChildNode(sibling);
            this.root = newRoot;

            in.parent = newRoot;
            sibling.parent = newRoot;

        } else {

            parent.keys[parent.degree - 1] = newParentKey;
            Arrays.sort(parent.keys, 0, parent.degree);

            int pointerIndex = parent.findChildIndex(in) + 1;
            parent.insertChildNode(sibling, pointerIndex);
            sibling.parent = parent;
        }
    }

    /**
     * Split the array of keys, starting from the index of pos
     * @param keys
     * @param pos
     * @return
     */
    private Integer[] splitKeys(Integer[] keys, int pos) {

        Integer[] halfKeys = new Integer[this.m];

        keys[pos] = null;
        
        // Split the array of keys from the index of cut
        for (int i = pos + 1; i < keys.length; i++) {
            halfKeys[i - pos - 1] = keys[i];
            keys[i] = null;
        }

        return halfKeys;
    }

    // Insert a new key-value pair into the B+ Tree
    public void insert(int key, int value) {
        // If the B+ tree is empty
        if (isEmpty()) {
            // Create a new leaf node by this key-value pair
            LeafNode leaf = new LeafNode(this.m, new Pair(key, value));
            // Add it as the first leaf node of this B+ tree
            this.firstLeaf = leaf;
        } else {
            // If the B+ tree is not empty
            LeafNode leaf = (this.root == null) ? this.firstLeaf : findLeafNode(key);
            
            // If insert is not successful(full)
            if (!leaf.insert(new Pair(key, value))) {

                // leaf.dictionary[leaf.numPairs]
                // add the new element to the end of dict[]
                leaf.getDict()[leaf.getNumOfPairs()] = new Pair(key, value);
                // leaf.numPairs++;
                // add one to the number of pairs
                leaf.setNumOfPairs(leaf.getNumOfPairs() + 1);
                // sort dict according to the comparator
                sortDict(leaf.getDict());
                
                // split the dict of this leaf node from midpoint
                int midpoint = getMidpoint();
                Pair[] halfDict = splitDict(leaf, midpoint);

                // If the parent for this leaf node is null
                if (leaf.parent == null) {
                    // Create new parent for this leaf
                    Integer[] parentKeys = new Integer[this.m];
                    parentKeys[0] = halfDict[0].key;
                    InternalNode parent = new InternalNode(this.m, parentKeys);
                    leaf.parent = parent;
                    parent.appendChildNode(leaf);

                } else {
                    int newParentKey = halfDict[0].key;
                    leaf.parent.keys[leaf.parent.degree - 1] = newParentKey;
                    Arrays.sort(leaf.parent.keys, 0, leaf.parent.degree);
                }

                // Create a new leaf node
                LeafNode newLeaf = new LeafNode(this.m, halfDict, leaf.parent);
                
                int pointerIndex = leaf.parent.findChildIndex(leaf) + 1;
                leaf.parent.insertChildNode(newLeaf, pointerIndex);

                newLeaf.right = leaf.right;
                if (newLeaf.right != null) {
                    newLeaf.right.left = newLeaf;
                }
                leaf.right = newLeaf;
                newLeaf.left = leaf;

                if (this.root == null) {
                    this.root = leaf.parent;
                } else {
                    InternalNode in = leaf.parent;
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
    public int search(int key) {

        // return -1 if is empty
        if (isEmpty()) {
            return -1;
        }

        // find the leaf node of this key
        LeafNode leaf = (this.root == null) ? this.firstLeaf : findLeafNode(key);

        Pair[] pairs = leaf.getDict();
        // call binary search for the key in pairs
        int index = binarySearch(pairs, leaf.getNumOfPairs(), key);

        if (index < 0) {
            return -1;
        } else {
            return pairs[index].value;
        }
    }

    // Search a key specified with lowerBound and upperBound in the B+ tree - range query
    public ArrayList<Integer> search(int lowerBound, int upperBound) {

        ArrayList<Integer> values = new ArrayList<Integer>();

        LeafNode currNode = this.firstLeaf;
        while (currNode != null) {
            Pair pairs[] = currNode.dict;
            // iterate through the pair of this node
            for (Pair pair : pairs) {
                // break if pair is null
                if (pair == null) {
                    break;
                }
                // add if in the specified range
                if (lowerBound <= pair.key && pair.key <= upperBound) {
                    values.add(pair.value);
                }
            }
            // continue search on the right sibling of this current node
            currNode = currNode.right;
        }

        return values;
    }
    
    public InternalNode getRoot() {
        return root;
    }

//    public static void main(String[] args) {
//        BPlusTree bpt = null;
//        bpt = new BPlusTree(3);
//        bpt.insert(5, 33);
//        bpt.insert(15, 21);
//        bpt.insert(25, 31);
//        bpt.insert(35, 41);
//        bpt.insert(45, 10);
//
//        if (bpt.search(15) != -1) {
//            System.out.println("Found");
//        } else {
//            System.out.println("Not Found");
//        }
//        
//    }

    

    

    
}
package bptree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BPlusTreeTest {
    BPlusTree bp;
    
    @Before
    public void before() {
        bp = new BPlusTree(4); 
        bp.insert(1912, 2101);
        bp.insert(1913, 2646);
        bp.insert(1915, 5060);
        bp.insert(1916, 6617);
        bp.insert(1916, 6864);
        bp.insert(1917, 7983);
        bp.insert(1918, 9369);
        bp.insert(1919, 10208);
        bp.insert(1919, 10298);
        bp.insert(1919, 10598);
        bp.insert(1919, 10844);
        bp.insert(1920, 11157);
        bp.insert(1921, 12027);
        bp.insert(1921, 12134);
        bp.insert(1921, 12136);
        bp.insert(1921, 12249);
        bp.insert(1921, 12281);
        bp.insert(1922, 13469);
        bp.insert(1923, 14397);
        bp.insert(1924, 14646);
        bp.insert(1925, 15361);
        bp.insert(1925, 16013);
        bp.insert(1926, 15883);
        bp.insert(2008, 1145152);
        bp.insert(2008, 1145446);
        bp.insert(2017, 6990206);
        bp.insert(2019, 9906644);
        bp.insert(2020, 9612136);
    }

    @Test
    public void testSearchInt() {
        // search for a specific year's movie
        assertEquals(2101, bp.search(1912));
        assertEquals(9906644, bp.search(2019));
    }

    @Test
    public void testSearchIntInt() {
        // search by range of year
        ArrayList<Integer> list1 = bp.search(2008, 2008);
        assertEquals(2, list1.size());
        // the order within the same key is guaranteed by the sort method in insert
        assertEquals(Integer.valueOf(1145152), Integer.valueOf(list1.get(0)));
        
        ArrayList<Integer> list2 = bp.search(1912, 1915);
        assertEquals(3, list2.size());
        // the order within different key is guaranteed by the add order in search
        assertEquals(Integer.valueOf(2101), Integer.valueOf(list2.get(0)));
    }
    
    @Test
    public void testIsLeafInternalNode() {
        assertFalse(bp.getRoot().isLeaf());
    }

}

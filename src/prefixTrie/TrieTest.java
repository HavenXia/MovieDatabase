package prefixTrie;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class TrieTest {

    @Test
    public void testBuildTrie() {
        
        Trie trie = new Trie();

        trie.buildTrie("trieData.txt");

        // test prefix trie search result
        List<ITerm> result = trie.getSuggestions("cleopat");
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(), "cleopatra");
        assertEquals(result.get(0), new Term("cleopatra", 2101));
    }

}

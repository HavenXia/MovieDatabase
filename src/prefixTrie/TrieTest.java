package prefixTrie;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class TrieTest {

    @Test
    public void testBuildTrie() {
        
        Trie trie = new Trie();

        trie.buildTrie("trieData.txt");
        
        List<ITerm> result = trie.getSuggestions("cleo");
        
        assertEquals(result.size(), 1);
    }

}

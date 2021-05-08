package prefixTrie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trie implements ITrie {

    private TNode root;
    
    // constructor
    public Trie() {
        this.root = new TNode("", 0);
    }
    
    @Override
    public void buildTrie(String filename) {
        
        // null case
        if (filename == null || filename.length() == 0) {
            return;
        }
        
        // the input file format is 'movieId title'
        File file = new File(filename);
        BufferedReader br = null;
        
        // first open the file with BufferReader
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  
        
        // use while loop to read each line
        String line;
        try {
            // ignore first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] items = line.trim().split("\\s+");
                
                // check for elements, must be movieId and title
                if (items.length < 2) {
                    continue;
                }
                // check style
                int styleCheck = 0;
                
                // check movieId
                for (char c: items[0].toCharArray()) {
                    if (!Character.isDigit(c)) {
                        styleCheck = 1;
                    }
                }
                
                // check word
                for (char c: items[1].toLowerCase().toCharArray()) {
                    if (c - 'a' < 0 || c - 'a' > 25) {
                        styleCheck = 1;
                    }
                }
                
                // if style error, jump to next line
                if (styleCheck == 1) {
                    continue;
                }
                
                // extract value;
                int id = Integer.parseInt(items[0]);
                String word = items[1].toLowerCase();
                
                // add the movie title with its id
                addWord(word, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Helper method for build Trie, add each movie title into the Trie
     * @param id
     * @param weight
     */
    private void addWord(String word, int id) {
        
        // do nothing if word is null or empty string
        if (word == null || word.equals("")) {
            return;
        }
        
        String wordToAdd = word.toLowerCase();
        TNode cur = this.root;
        
        // use for loop to add each title into it 
        for (int i = 0; i < wordToAdd.length(); i++) {
            
            char c = wordToAdd.charAt(i);
            cur.setPrefixes(cur.getPrefixes() + 1);
            
            // if has contain current char, update its fields
            if (cur.references[c - 'a'] != null) {
                
                // for internal nodes, prefixes + 1
                cur = cur.references[c - 'a'];
                
                // if reach last element, also need to update the word node
                // update both prefix and word
                if (i == word.length() - 1) {
                    cur.setTerm(new Term(wordToAdd, id));
                    cur.setWords(cur.getWords() + 1);
                    cur.setPrefixes(cur.getPrefixes() + 1);
                }
            } else {
                
                // if reach last element, need to append a word node
                if (i == word.length() - 1) {
                    TNode wordNode = new TNode(wordToAdd, id);
                    wordNode.setWords(wordNode.getWords() + 1);
                    wordNode.setPrefixes(wordNode.getPrefixes() + 1);
                    cur.setReference(c - 'a', wordNode);
                    
                } else {
                    // for internal nodes, just create one
                    TNode newNode = new TNode();
                    cur.setReference(c - 'a', newNode);
                    cur = cur.references[c - 'a'];
                } 
            }
        }
    }

    @Override
    public List<ITerm> getSuggestions(String prefix) {
        List<ITerm> result = new ArrayList<ITerm>();
        // null case
        if (prefix == null) {
            return result;
        }
        
        // check prefix style
        for (char c: prefix.toLowerCase().toCharArray()) {
            if (c - 'a' < 0 || c - 'a' > 25) {
                return result;
            }
        }
        
        // get the array of TrieNodes
        TNode cur = getSubTrie(prefix);
        addSuggestions(cur, result);
        //Collections.sort(result);
        return result;
    }

    /**
     * return the subTrie root node begin with given prefix
     * @param prefix
     * @return
     */
    private TNode getSubTrie(String prefix) {
        // edge cases
        if (prefix == null) {
            return null;
        }
        if (prefix.length() == 0) {
            return this.root;
        }
        
        // check format
        for (char c: prefix.toLowerCase().toCharArray()) {
            if (c - 'a' < 0 || c - 'a' > 25) {
                return null;
            }
        }
        String prefixLow = prefix.toLowerCase();
        
        TNode cur = this.root;
        // go down through the Trie
        for (int i = 0; i < prefixLow.length(); i++) {
            
            char c = prefixLow.charAt(i);
            // if contains, update cur
            if (cur.references[c - 'a'] != null) {
                // when continue to the last char, should return copy
                if (i == prefixLow.length() - 1) {
                    return cur.getReferences()[c - 'a'];
                } else {
                    // else, we just update cur to next node
                    cur = cur.references[c - 'a'];
                }
            } else {
                return null;
            }
        }
        return null;
    }
    
    /**
     * Recursively add all nodes and copy their value into 
     * the arraylist to ensure encapsulation
     * @param cur
     * @param result
     */
    private void addSuggestions(TNode node, List<ITerm> result) {
        if (node == null) {
            return;
        }
        // if reach words, add it into result
        if (node.getWords() == 1) {
            // copy the Term object to ensure encapsulation
            Term origin = node.getTerm();
            Term copy = new Term(origin.getTitle(), origin.getId());
            result.add((ITerm) copy);
        }
        // dfs
        for (int i = 0; i < 26; i++) {
            if (node.references[i] != null) {
                addSuggestions(node.references[i], result);
            }
        }
    }
    
    
    
    

}

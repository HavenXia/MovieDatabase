package PrefixTrie;

/**
 * Node for Trie datastructure
 * @author haoyu xia
 *
 */
public class TNode {
    
    private Term term;
    private int words;
    private int prefixes;
    protected TNode[] references;
    private final int r = 26;
    
    // constructor for node that end as a word
    public TNode(String title, long id) {
        
        if (title == null || id < 0) {
            throw new IllegalArgumentException();
        }
        
        this.term = new Term(title, id);
        this.prefixes = 0;
        this.words = 0;
        this.references = new TNode[r];
    }
    
    // constructor for node that just work as prefix
    public TNode() {
        this.prefixes = 0;
        this.words = 0;
        this.references = new TNode[r];
    }
    
    // getters and setters
    public Term getTerm() {
        return this.term;
    }
    
    public void setTerm(Term term) {
        this.term = term;
    }
    
    public int getWords() {
        return this.words;
    }
    
    public void setWords(int words) {
        this.words = words;
    }
    
    public int getPrefixes() {
        return this.prefixes;
    }
    
    public void setPrefixes(int prefixes) {
        this.prefixes = prefixes;
    }
    
    
    // getter and setter for references should ensure encapsulation
    // so just allow the classes in same package to modify it
    // so use protected keyword 
    protected TNode[] getReferences() {
        return this.references;
    }
    
    protected void setReference(int index, TNode node) {
        this.references[index] = node;
    }
    
}

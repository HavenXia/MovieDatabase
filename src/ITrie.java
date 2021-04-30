import java.util.List;

/**
 * Interface for Trie
 */
public interface ITrie {
    
    /**
     * Initializes the Trie with input file
     * 
     * @param filename the file to read all the movie data from each line
     *          contains a word and its weight(here all set to 1) This method 
     *          will call the addWord method in future implementation   
     * @return void       
     */
    public void buildTrie(String filename);
    

    /**
     * Return the search result based on the built Trie
     * @param prefix
     * @return a List containing all the ITerm objects with query starting with
     *          prefix. Return an empty list if there are no ITerm object 
     *          starting with prefix.
     */
    public List<ITerm> getSuggestions(String prefix);
}

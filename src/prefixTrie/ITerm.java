package prefixTrie;

/**
 * Interface for Term
 */
public interface ITerm {

    /**
     * Compares the two terms in lexicographic order by query.
     * 
     * @param another Iterm to compare
     * @return compare result
     */
    // 
    public int compareTo(ITerm that);
    
    /**
     * @return the movie title of current Iterm
     */
    public String getTitle(); 
}

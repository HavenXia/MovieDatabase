package prefixTrie;

/**
 * Interface for Term
 */
public interface ITerm {

    /**
     * Compares the two terms in lexicographic order by query.
     * @param that ITerm to compare
     * @return compare result
     */
    public int compareTo(ITerm that);
    
    /**
     * Get Title of an ITerm
     * @return the movie title of current Iterm
     */
    public String getTitle(); 
}

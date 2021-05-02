import java.util.Comparator;

/**
 * Interface for Term
 */
public interface ITerm extends Comparable<ITerm> {

    /**
     * Compares the two terms in lexicographic order by query.
     * 
     * @param another Iterm to compare
     * @return compare result
     */
    // 
    public int compareTo(ITerm that);
    
    /**
     * @return the string of current Iterm
     */
    public String getTerm(); 
}

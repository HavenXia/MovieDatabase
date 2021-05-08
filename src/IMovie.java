import java.util.Comparator;

/**
 * Movie interface
 *
 */
public interface IMovie {
    
    
    /**
     * Compares the two movies in descending order by rating.
     * 
     * @return comparator Object
     */
    public static Comparator<IMovie> byDescendingRatingOrder() {
        
        return new Comparator<IMovie>() {
            @Override
            public int compare(IMovie o1, IMovie o2) {
                Movie movie1 = (Movie) o1;
                Movie movie2 = (Movie) o2;
                return movie1.getRating() - movie2.getRating() > 0 ? -1 : 1;
            }
        };
    }
    
    /**
     * Compares the two movies in descending order by year.
     * 
     * @return comparator Object
     */
    public static Comparator<IMovie> byDescendingYearOrder() {
        
        return new Comparator<IMovie>() {
            @Override
            public int compare(IMovie o1, IMovie o2) {
                Movie movie1 = (Movie) o1;
                Movie movie2 = (Movie) o2;
                return movie1.getYear() - movie2.getYear() > 0 ? -1 : 1;
            }
        };
    }
    
    /**
     * Compares the two movies in lexicographic order 
     * 
     * @return comparator Object
     */
    public static Comparator<IMovie> byLexicographicOrder() {
        
        return new Comparator<IMovie>() {
            @Override
            public int compare(IMovie o1, IMovie o2) {
                Movie movie1 = (Movie) o1;
                Movie movie2 = (Movie) o2;
                return movie1.getName().compareTo(movie2.getName());
            }
        };
    }
    
}

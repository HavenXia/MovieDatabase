import java.util.List;

/**
 * Interface for Database
 */
public interface IDatabase {

    
    /**
     * Construct
     *  + a Trie
     *  + a B+ Tree
     *  + a HashMap<IMDB_id, Movie>
     *  + a List of HashMap<genre, List<IMDB_id>>
     * form the input file and contruct Movie object for each movie
     * 
     * @param filePath the path of the data
     * @return 
     */
    public void loadDatabase(String filePath);
    
    
    
    /**
     * Search through Trie build in loadDatabase and return
     * the result from getSuggestions in Trie class
     * 
     * @param title title of the movie to query
     * @return a list of Movie objects that begin with the 
     *          title, like "avengers" will return 4 movies
     */
    public List<Movie> searchByTitle(String title);
    
    
    /**
     * Search through B+ tree built in loadDatabase 
     * and return the result from find 
     * 
     * @param startYear and endYear represent the range
     *          used to search for movies
     * @return a list of Movie objects that exactly in 
     *          the input time range
     */
    public List<Movie> searchByYear(int startYear, int endYear);
    
    
    /**
     * Search through B+ tree built in loadDatabase 
     * and return the result from find 
     * 
     * @param lowerBound and upperBound represent the 
     *         range of ratings used to search for movies
     * @return a list of Movie objects that exactly in 
     *          the input rating range
     */
    public List<Movie> searchByRating(double lowerBound, double upperBound);
    
    
    
    /**
     * Find the intersections of different List of movies
     * with corresponding genre requests
     * 
     * @param genres some categories that used to find intersection
     * @return a list of Movie objects that have required genres 
     */
    public List<Movie> searchByRating(List<String> genres);
}

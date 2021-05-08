import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DataBaseTest {

    @Test
    public void testDatabase() {
        
        DataBase db = new DataBase();
        db.loadDatabase("filtered_database.txt");
        
        // test search by movie title
        List<Movie> result = db.searchByTitle("cleopa");
        assertEquals(result.size(), 1);
        // test movie properties
        assertEquals(result.get(0).getName(), "Cleopatra");
        assertEquals(result.get(0).getId(), 2101);
        assertEquals(result.get(0).getYear(), 1912);
        assertEquals(result.get(0).getGenre().size(), 2);
        assertEquals(result.get(0).getDirector(), "Charles L. Gaskill");
        assertEquals(result.get(0).getRating(), 5.2, 0.1);
        
        result = db.searchByTitle("dis");
        assertEquals(result.size(), 44);
        
        // test search by year
        result = db.searchByYear(1912, 1912);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "Cleopatra");
        
        // there are 288 movies in 1950-1960 inclusive
        result = db.searchByYear(1950, 1960);
        assertEquals(result.size(), 288);
        
        // test search by rating
        result = db.searchByRating(10.0, 10.0);
        assertEquals(result.size(), 0);
        
        // only 14 movies are in 9.0 - 9.9 rating
        result = db.searchByRating(9.0, 9.9);
        assertEquals(result.size(), 14);
        
        // test search by genres
        List<String> genres = new ArrayList<String>();
        genres.add("Drama");
        result = db.searchByGenres(genres);
        
        // 8587 comedy movies
        assertEquals(result.size(), 8587);
        
        // Comedy and History intersection
        genres.add("History");
        result = db.searchByGenres(genres);
        assertEquals(result.size(), 347);
    }

}

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class DataBaseTest {

    @Test
    public void testDatabase() {
        
        DataBase db = new DataBase();
        db.loadDatabase("filtered_database.txt");
        List<Movie> result = db.searchByTitle("cleo");
        assertEquals(result.size(), 1);
    }

}

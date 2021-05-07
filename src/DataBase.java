import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PrefixTrie.Trie;
import bptree.BPlusTree;

public class DataBase implements IDatabase {

    private Map<Integer, Movie> movieIndex;
    private Trie trie;
    private BPlusTree years;
    private BPlusTree ratings;
    private Map<String, List<Movie>> genres;
    
    
    // constructor that initialize 
    // one trie
    // two BPTree 
    // HashMap of IMDBid to Movie object
    // List of HashMap of genre to Movie object
    public DataBase() {
        this.movieIndex = new HashMap<Integer, Movie>();
        this.trie = new Trie();
        // use order 3 BPTree
        this.years = new BPlusTree(3);
        this.ratings = new BPlusTree(3);
        this.genres = new HashMap<String, List<Movie>>();
    }
    
    
    @Override
    public void loadDatabase(String filePath) {
        
        // null case
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        
        // initialize the BufferReader
        File file = new File(filePath);
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
            // read heading first
            br.readLine();
            // then use while loop to read each line and parse
            while ((line = br.readLine()) != null) {
                
                // parse each line by tab
                String[] properties = line.trim().split("\\t");
                // check number of elements
                if (properties.length != 6) {
                    continue;
                }
                
                // create Movie object for each line
                
                
                
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }

    @Override
    public List<Movie> searchByTitle(String title) {
        
        return null;
    }

    @Override
    public List<Movie> searchByYear(int startYear, int endYear) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Movie> searchByRating(double lowerBound, double upperBound) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Movie> searchByRating(List<String> genres) {
        // TODO Auto-generated method stub
        return null;
    }

}

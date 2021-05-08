import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bptree.BPlusTree;
import prefixTrie.ITerm;
import prefixTrie.Term;
import prefixTrie.Trie;

public class DataBase implements IDatabase {

    private Map<Integer, Movie> movieIndex;
    private Trie trie;
    private BPlusTree movieByYear;
    private BPlusTree movieByRating;
    private Map<String, Set<Integer>> movieByGenre;
    
    
    // constructor that initialize 
    // one trie
    // two BPTree 
    // HashMap of IMDBid to Movie object
    // List of HashMap of genre to Movie id
    public DataBase() {
        this.movieIndex = new HashMap<Integer, Movie>();
        this.trie = new Trie();
        // use order 3 BPTree
        this.movieByYear = new BPlusTree(3);
        this.movieByRating = new BPlusTree(3);
        this.movieByGenre = new HashMap<String, Set<Integer>>();
    }
    
    
    @Override
    public void loadDatabase(String filePath) {
        
        // null case
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        
        // initialize the BufferReader and BufferWriter
        File file = new File(filePath);
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        // first open the file with BufferReader
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        
        // use while loop to read each line
        String line;
        try {
            // initialize the BufferedWriter
            bw = new BufferedWriter(new FileWriter("trieData.txt"));
            bw.write("movieId title\n");
            
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
                // parse properties
                int id = Integer.parseInt(properties[0]);
                int year = Integer.parseInt(properties[2]);
                double rating = Double.parseDouble(properties[5]);
                
                // parse the genres into an arrayList
                List<String> genres = Arrays.asList(properties[3].trim().split(", "));
                // create Movie object for each line
                Movie cur = new Movie(id, properties[1], year, genres, properties[4], rating);
                // add each id-movie pair into movieIndex
                movieIndex.put(id, cur);
                
                // write current id-movie pair to trie
                bw.write(properties[0] + " " + properties[1] + "\n");
                
                // insert year-movieId and rating-movieId into two BPTree
                // rating * 10 to convert it to integer
                movieByYear.insert(year, id);
                movieByRating.insert((int) (rating * 10), id);
                
                // check if current genre has create
                // add current movie id to all its genres' HashMap
                for (String genre: genres) {
                    if (!movieByGenre.containsKey(genre)) {
                        // put new List into the HashMap
                        movieByGenre.put(genre, new HashSet<Integer>());
                    }
                    movieByGenre.get(genre).add(id);
                }
            }
            bw.close();
            // use the id-movie pairs in trieData.txt to build trie
            trie.buildTrie("trieData.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        
        List<Movie> result = new ArrayList<Movie>();
        // null case
        if (title == null || title.length() == 0) {
            return result;
        }
        
        // first get the list of ITerms with title as prefix
        List<ITerm> terms = this.trie.getSuggestions(title);
        
        // query each movie object with the Iterm id
        for (ITerm term: terms) {
            Term temp = (Term) term;
            result.add(movieIndex.get(temp.getId()));
        }
        
        // strategy pattern: sort movies by movie title
        Collections.sort(result, IMovie.byLexicographicOrder());
        
        return result;
    }

    @Override
    public List<Movie> searchByYear(int startYear, int endYear) {
        
        List<Movie> result = new ArrayList<Movie>();
        
        // check valid year (1912-2019)
        if (startYear < 1912 || startYear > 2019 || endYear < 1912 || endYear > 2019) {
            return result;
        }
        
        List<Integer> ids = this.movieByYear.search(startYear, endYear);
        
        // query each movie object with the Iterm id
        for (int id: ids) {
            result.add(movieIndex.get(id));
        }
        
        // strategy pattern: sort movies by year
        Collections.sort(result, IMovie.byDescendingYearOrder());
        
        return result;
    }

    @Override
    public List<Movie> searchByRating(double lowerBound, double upperBound) {
        
        List<Movie> result = new ArrayList<Movie>();
        
        // check valid rating (0-10)
        if (lowerBound < 0 || lowerBound > 10 || upperBound < 0 || upperBound > 10) {
            return result;
        }
        
        List<Integer> ids = this.movieByRating.search((int)(lowerBound * 10)
                , (int)(upperBound * 10));
        
        // query each movie object with the Iterm id
        for (int id: ids) {
            result.add(movieIndex.get(id));
        }
        
        // strategy pattern: sort movies by rating
        Collections.sort(result, IMovie.byDescendingRatingOrder());
        
        return result;
    }

    @Override
    public List<Movie> searchByGenres(List<String> genres) {
        
        List<Movie> result = new ArrayList<Movie>();
        
        // null case
        if (genres == null || genres.size() == 0) {
            return result;
        }
        
        // check if there is invalid input
        for (String genre: genres) {
            if (!movieByGenre.containsKey(genre)) {
                return result;
            }
        }
         
        // use retainAll method to handle 
        Set <Integer> ids = new HashSet<Integer>();
        
        // add the first HashSet into ids
        ids.addAll(movieByGenre.get(genres.get(0)));
        
        // for loop and use retain all to get intersection
        for (int i = 1; i < genres.size(); i++) {
            Set<Integer> temp = movieByGenre.get(genres.get(i));
            ids.retainAll(temp);
        }
        
        // query each movie object with the Iterm id
        for (int id: ids) {
            result.add(movieIndex.get(id));
        }
        
        return result;
    }
    
    // return a list of all genres
    public List<String> getGenres() {
        return new ArrayList<String>(this.movieByGenre.keySet());
    }

}

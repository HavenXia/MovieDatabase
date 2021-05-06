package PrefixTrie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Trie implements ITrie {

    @Override
    public void buildTrie(String filename) {
        
        // null case
        if (filename == null || filename.length() == 0) {
            return;
        }
        
        // the input file format is 'movieId title'
        File file = new File(filename);
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
            // ignore first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] items = line.trim().split("\\s+");
                
                // check for elements, must be movieId and title
                if (items.length < 2) {
                    continue;
                }
                // check style
                int styleCheck = 0;
                
                // check movieId
                for (char c: items[0].toCharArray()) {
                    if (!Character.isDigit(c)) {
                        styleCheck = 1;
                    }
                }
                // check title
                for (char c: items[1].toLowerCase().toCharArray()) {
                    if (c - 'a' < 0 || c - 'a' > 25) {
                        styleCheck = 1;
                    }
                }
                // if style error, jump to next line
                if (styleCheck == 1) {
                    continue;
                }
                
                // extract value;
                long id = Long.parseLong(items[0]);
                String word = items[1].toLowerCase();
                
                // add the movie title with its id
                addWord(word, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Helper method for build Trie
     * @param id
     * @param weight
     */
    private void addWord(String word, long id) {
        
        
    }

    @Override
    public List<ITerm> getSuggestions(String prefix) {
        // TODO Auto-generated method stub
        return null;
    }

}

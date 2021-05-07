import java.util.List;

public class Movie implements IMovie{

    private String name;
    private int id;
    private int year;
    private Double rating;
    private String director;
    private List<String> genre;
    
    // constructor
    public Movie(String title, int id, int year, double rating, 
            String director, List<String> genre) {
        this.name = title;
        this.id = id;
        this.year = year;
        this.rating = rating;
        this.director = director;
        this.genre = genre;
    }
    
    
    
    @Override
    public void printMovie() {
        // TODO print per requirements
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    

}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Runner for the database
 * @author parallax
 *
 */
public class Runner {
    
    public static void main(String[] args) {
        DataBase db = new DataBase();
        
        // load the database
        db.loadDatabase("filtered_database.txt");
        
        // print welcome
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("##      ## ######## ##        ######   #######  ##     ## ######## \n"
                + "##  ##  ## ##       ##       ##    ## ##     ## ###   ### ##       \n"
                + "##  ##  ## ##       ##       ##       ##     ## #### #### ##       \n"
                + "##  ##  ## ######   ##       ##       ##     ## ## ### ## ######   \n"
                + "##  ##  ## ##       ##       ##       ##     ## ##     ## ##       \n"
                + "##  ##  ## ##       ##       ##    ## ##     ## ##     ## ##       \n"
                + " ###  ###  ######## ########  ######   #######  ##     ## ######## ");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println();
        
        
        // given choices
        System.out.println("\n\n*****************************************************************");
        System.out.println("******************  πͺπ―πΆπΆπΊπ¬ πΊπ¬π¨πΉπͺπ― π΄π¬π»π―πΆπ«  ******************");
        System.out.println("**  1 - πΊπ¬π¨πΉπͺπ― π©π π΅π¨π΄π¬        2 - πΊπ¬π¨πΉπͺπ― π©π ππ¬π¨πΉ πΉπ¨π΅π?π¬  **");
        System.out.println("**  3 - πΊπ¬π¨πΉπͺπ― π©π πΉπ¨π»π°π΅π? πΉπ¨π΅π?π¬     4 - πΊπ¬π¨πΉπͺπ― π©π π?π¬π΅πΉπ¬πΊ **");
        System.out.println("*****************************************************************");
        
        // take user input
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        scan.nextLine();
        List<Movie> result = null;
        
        // process different searching
        if (s.equals("1")) {
            System.out.println("\n*****************************************************************");
            System.out.println("*******************     πΊπ¬π¨πΉπͺπ― π©π π΅π¨π΄π¬     *******************");
            System.out.println("π¬π΅π»π¬πΉ π»π―π¬ π΄πΆπ½π°π¬ π΅π¨π΄π¬:");
            s = scan.next();
            scan.nextLine();
            result = db.searchByTitle(s);
        } else if (s.equals("2")) {
            System.out.println("\n*****************************************************************");
            System.out.println("***************     πΊπ¬π¨πΉπͺπ― π©π ππ¬π¨πΉ πΉπ¨π΅π?π¬     ****************");
            System.out.println("π¬π΅π»π¬πΉ π»π―π¬ ππ¬π¨πΉ πΉπ¨π΅π?π¬: (π»πΎπΆ π°π΅π»π¬π?π¬πΉ πΊπ·π³π°π» πΎπ°π»π― πΎπ―π°π»π¬ πΊπ·π¨πͺπ¬)");
            int startYear = scan.nextInt();
            int endYear = scan.nextInt();
            scan.nextLine();
            result = db.searchByYear(startYear, endYear);
        } else if (s.equals("3")) {
            System.out.println("\n*****************************************************************");
            System.out.println("**************     πΊπ¬π¨πΉπͺπ― π©π πΉπ¨π»π°π΅π? πΉπ¨π΅π?π¬     ***************");
            System.out.println("π¬π΅π»π¬πΉ π»π―π¬ πΉπ¨π»π°π΅π? πΉπ¨π΅π?π¬: (π»πΎπΆ π«πΆπΌπ©π³π¬ πΊπ·π³π°π» πΎπ°π»π― πΎπ―π°π»π¬ πΊπ·π¨πͺπ¬)");
            double lowerBound = scan.nextDouble();
            double upperBound = scan.nextDouble();
            scan.nextLine();
            result = db.searchByRating(lowerBound, upperBound);
        } else if (s.equals("4")) {
            
            // given existing genres
            System.out.println("\n\n*****************************************************************");
            System.out.println("*****************************************************************");
            System.out.println("**************************  πΊπΈπππΈπ  ****************************");
            
            List<String> genres = db.getGenres();
            int size = genres.size();
            for (int i = 0; i < genres.size(); i += 4) {
                System.out.print("            ");
                System.out.print(genres.get(i) + "    ");
                if (i + 1 >= size) {
                    break;
                }
                System.out.print(genres.get(i + 1) + "    ");
                if (i + 2 >= size) {
                    break;
                }
                System.out.print(genres.get(i + 2) + "    ");
                if (i + 3 >= size) {
                    break;
                }
                System.out.print(genres.get(i + 3) + "    ");
                System.out.print("\n");
            }
            System.out.println("\n*****************************************************************");
            System.out.println("*****************************************************************");
            
            // receive input genres
            System.out.println("\n*****************************************************************");
            System.out.println("*****************     πΊπ¬π¨πΉπͺπ― π©π π?π¬π΅πΉπ¬πΊ     ******************");
            System.out.println("π¬π΅π»π¬πΉ π»π―π¬ π?π¬π΅πΉπ¬ π³π°πΊπ»: (πΊπ·π³π°π» πΎπ°π»π― πΎπ―π°π»π¬ πΊπ·π¨πͺπ¬)");
            s = scan.nextLine();
            String[] genreInput = s.split("\\s+");
            List<String> queries = new ArrayList<String>();
            for (String genre: genreInput) {
                queries.add(genre);
            }
            result = db.searchByGenres(queries);
        } else {
            return;
        }
        
        // print the movies
        String format = "| %-8s | %-15s | %-4s | %-8s | %-31s | %-28s | %n";
        System.out.format("+----------+-----------------+------+----------+---------------------------------+------------------------------+%n");
        System.out.format("|    ID    |      Title      | Year |  Rating  |             Genres              |            Director          |%n");
        System.out.format("+----------+-----------------+------+----------+---------------------------------+------------------------------+%n");
        for (int i = 0; i < result.size(); i++) {
            Movie movie = result.get(i);
            String curGenres = "";
            for (String genre: movie.getGenre()) {
                curGenres = curGenres + genre + "  ";
            }
            System.out.format(format, String.valueOf(movie.getId()), movie.getName(),
                    String.valueOf(movie.getYear()), String.valueOf(movie.getRating()),
                    curGenres, movie.getDirector().split(",")[0].trim());
        }
        System.out.format("+----------+-----------------+------+----------+---------------------------------+------------------------------+%n");
    }
}

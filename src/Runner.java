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
        System.out.println("******************  𝑪𝑯𝑶𝑶𝑺𝑬 𝑺𝑬𝑨𝑹𝑪𝑯 𝑴𝑬𝑻𝑯𝑶𝑫  ******************");
        System.out.println("**  1 - 𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑵𝑨𝑴𝑬        2 - 𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝒀𝑬𝑨𝑹 𝑹𝑨𝑵𝑮𝑬  **");
        System.out.println("**  3 - 𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑹𝑨𝑻𝑰𝑵𝑮 𝑹𝑨𝑵𝑮𝑬     4 - 𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑮𝑬𝑵𝑹𝑬𝑺 **");
        System.out.println("*****************************************************************");
        
        // take user input
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        scan.nextLine();
        List<Movie> result = null;
        
        // process different searching
        if (s.equals("1")) {
            System.out.println("\n*****************************************************************");
            System.out.println("*******************     𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑵𝑨𝑴𝑬     *******************");
            System.out.println("𝑬𝑵𝑻𝑬𝑹 𝑻𝑯𝑬 𝑴𝑶𝑽𝑰𝑬 𝑵𝑨𝑴𝑬:");
            s = scan.next();
            scan.nextLine();
            result = db.searchByTitle(s);
        } else if (s.equals("2")) {
            System.out.println("\n*****************************************************************");
            System.out.println("***************     𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝒀𝑬𝑨𝑹 𝑹𝑨𝑵𝑮𝑬     ****************");
            System.out.println("𝑬𝑵𝑻𝑬𝑹 𝑻𝑯𝑬 𝒀𝑬𝑨𝑹 𝑹𝑨𝑵𝑮𝑬: (𝑻𝑾𝑶 𝑰𝑵𝑻𝑬𝑮𝑬𝑹 𝑺𝑷𝑳𝑰𝑻 𝑾𝑰𝑻𝑯 𝑾𝑯𝑰𝑻𝑬 𝑺𝑷𝑨𝑪𝑬)");
            int startYear = scan.nextInt();
            int endYear = scan.nextInt();
            scan.nextLine();
            result = db.searchByYear(startYear, endYear);
        } else if (s.equals("3")) {
            System.out.println("\n*****************************************************************");
            System.out.println("**************     𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑹𝑨𝑻𝑰𝑵𝑮 𝑹𝑨𝑵𝑮𝑬     ***************");
            System.out.println("𝑬𝑵𝑻𝑬𝑹 𝑻𝑯𝑬 𝑹𝑨𝑻𝑰𝑵𝑮 𝑹𝑨𝑵𝑮𝑬: (𝑻𝑾𝑶 𝑫𝑶𝑼𝑩𝑳𝑬 𝑺𝑷𝑳𝑰𝑻 𝑾𝑰𝑻𝑯 𝑾𝑯𝑰𝑻𝑬 𝑺𝑷𝑨𝑪𝑬)");
            double lowerBound = scan.nextDouble();
            double upperBound = scan.nextDouble();
            scan.nextLine();
            result = db.searchByRating(lowerBound, upperBound);
        } else if (s.equals("4")) {
            
            // given existing genres
            System.out.println("\n\n*****************************************************************");
            System.out.println("*****************************************************************");
            System.out.println("**************************  𝐺𝐸𝑁𝑅𝐸𝑆  ****************************");
            
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
            System.out.println("*****************     𝑺𝑬𝑨𝑹𝑪𝑯 𝑩𝒀 𝑮𝑬𝑵𝑹𝑬𝑺     ******************");
            System.out.println("𝑬𝑵𝑻𝑬𝑹 𝑻𝑯𝑬 𝑮𝑬𝑵𝑹𝑬 𝑳𝑰𝑺𝑻: (𝑺𝑷𝑳𝑰𝑻 𝑾𝑰𝑻𝑯 𝑾𝑯𝑰𝑻𝑬 𝑺𝑷𝑨𝑪𝑬)");
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
        String format = "| %-8s | %-15s | %-4s | %-8s | %-30s | %-29s | %n";
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

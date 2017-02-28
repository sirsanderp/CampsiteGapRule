import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Sander Peerna on 2/24/2017.
 */
public class AvailableCampsites {
    public static void main (String[] args) {
        SearchDataParser parser;
        if (args.length == 0) parser = new SearchDataParser(new File("src/main/resources/test-case.json"));
        else parser = new SearchDataParser(new File(args[0]));

        try {
            SearchDatabase database = parser.buildSearchDatabase();
            SearchQuery query = parser.buildSearchQuery();
            ArrayList<String> result = query.findAvailableCampsites(database);

            for (String campsite : result) {
                System.out.println(campsite);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

import java.io.File;
import java.text.ParseException;

/**
 * Created by Sander Peerna on 2/24/2017.
 */
public class AvailableCampsites {
    public static void main (String[] args) {
        SearchDataParser parser = new SearchDataParser(new File("src/main/resources/test-case.json"));
        try {
            SearchDatabase database = parser.buildSearchDatabase();
            SearchQuery query = parser.buildSearchQuery();
            query.findAvailableCampsites(database);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        SearchDatabase database = new SearchDatabase();
//        database.addCampsite(1, "Grizzly Bear Fun Ground");
//        database.addCampsite(2, "Camp Campbell");
//        database.addReservation(1, 2, 4);
//        database.addReservation(1, 9, 12);
//        database.addReservation(2, 5, 8);
//        database.addGapRule(2);
//        database.addGapRule(3);
//        SearchQuery query = new SearchQuery(9, 12);
//        query.findAvailableCampsites(database);
//        query.updateQuery(12, 14);
//        query.findAvailableCampsites(database);
//        database.addGapRule(1);
//        database.removeGapRule(3);
//        query.findAvailableCampsites(database);
    }
}

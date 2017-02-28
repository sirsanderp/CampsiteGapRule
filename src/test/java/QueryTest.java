import org.junit.Test;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by sande on 2/28/2017.
 */
public class QueryTest {
    SearchDatabase database;
    SearchQuery query;

    public QueryTest() throws ParseException {
        SearchDataParser parser = new SearchDataParser(new File("src/main/resources/test-case.json"));
        database = parser.buildSearchDatabase();
        query = parser.buildSearchQuery();
    }

    @Test
    public void confirmBaseCase() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Daniel Boone Bungalow");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Bear Grylls Cozy Cave");
        expected.add("Wyatt Earp Corral");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    @Test
    public void changeReservationWindow1() {
        query.updateQuery(0, 5);
        query.updateQuery(1, 4);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Daniel Boone Bungalow");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Bear Grylls Cozy Cave");
        expected.add("Wyatt Earp Corral");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    @Test
    public void changeGapRule1() {
        database.addGapRule(1);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Daniel Boone Bungalow");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Bear Grylls Cozy Cave");
        expected.add("Wyatt Earp Corral");
        assertEquals(expected, query.findAvailableCampsites(database));
    }
}

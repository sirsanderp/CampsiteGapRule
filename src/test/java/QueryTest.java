import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test different queries for a given JSON input.
 */
public class QueryTest {
    CampsiteDatabase database;
    CampsiteQuery query;

    /**
     * Build the <code>CampsiteDatabase</code> and <code>CampsiteQuery</code> objects to use for testing.
     * @throws ParseException
     * @throws IOException
     */
    public QueryTest() throws ParseException, IOException {
        CampsiteDataParser parser = new CampsiteDataParser(new File("src/main/resources/test-case.json"));
        database = parser.buildSearchDatabase();
        query = parser.buildSearchQuery();
    }

    /**
     * Query for the reservation given in the JSON input.
     */
    @Test
    public void confirmBaseTestCase() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Daniel Boone Bungalow");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Bear Grylls Cozy Cave");
        expected.add("Wyatt Earp Corral");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    /**
     *  Query for a reservation from June 1 to June 2.
     *  Assume dates not in the JSON are not reserved.
     */
    @Test
    public void changeReservationWindow1() {
        query.updateQuery(153, 154);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Lewis and Clark Camp Spot");
        expected.add("Jonny Appleseed Log Cabin");
        expected.add("Davey Crockett Camphouse");
        expected.add("Daniel Boone Bungalow");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Edmund Hillary Igloo");
        expected.add("Wyatt Earp Corral");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    /**
     * Query for reservation from June 10 to June 12.
     */
    @Test
    public void changeReservationWindow2() {
        query.updateQuery(162, 164);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Lewis and Clark Camp Spot");
        expected.add("Davey Crockett Camphouse");
        expected.add("Edmund Hillary Igloo");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    /**
     * Query for a reservation from June 16 to June 18.
     */
    @Test
    public void changeReservationWindow3() {
        query.updateQuery(168, 170); // June 16 - June 18
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Lewis and Clark Camp Spot");
        expected.add("Davey Crockett Camphouse");
        expected.add("Daniel Boone Bungalow");
        expected.add("Bear Grylls Cozy Cave");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    /**
     * Query for a reservation from June 7 to June 10 with a gap rule of only 1 day.
     */
    @Test
    public void changeGapRule1() {
        database.addGapRule(1);
        database.removeGapRule(2);
        database.removeGapRule(3);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Grizzly Adams Adventure Cabin");
        expected.add("Jonny Appleseed Log Cabin");
        expected.add("Teddy Rosevelt Tent Site");
        expected.add("Bear Grylls Cozy Cave");
        assertEquals(expected, query.findAvailableCampsites(database));
    }

    /**
     * Query for a reservation from June 10 to June 11 with gap rules of 2, 3, and 4.
     */
    @Test
    public void changeGapRule2() {
        query.updateQuery(162, 163);
        database.addGapRule(4);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Lewis and Clark Camp Spot");
        expected.add("Edmund Hillary Igloo");
        assertEquals(expected, query.findAvailableCampsites(database));
    }
}

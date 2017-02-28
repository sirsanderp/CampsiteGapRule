import org.junit.Test;

import java.io.File;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Created by Sander Peerna on 2/28/2017.
 */
public class DatabaseTest {
    SearchDatabase database;
    SearchQuery query;

    public DatabaseTest() throws ParseException {
        SearchDataParser parser = new SearchDataParser(new File("src/main/resources/test-case.json"));
        database = parser.buildSearchDatabase();
        query = parser.buildSearchQuery();
    }

    @Test
    public void confirmDatabaseEntries() {
        assertTrue(database.isReserved(4, 4));
        assertFalse(database.isReserved(6, 13));
    }

    @Test
    public void updateCampsites() {
        database.addCampsite(834822939, "Dave's Summer Swamp");
        database.addCampsite(1143627, "Tucker's Clear Lake");

        assertEquals(11, database.getCampsites().size());
        assertEquals("Jonny Appleseed Log Cabin", database.getCampsite(3));
        assertEquals("Tucker's Clear Lake", database.getCampsite(1143627));

        database.removeCampsite(1143627);
        assertNull(database.getCampsite(1143627));
    }

    @Test
    public void updateReservations() {
        assertFalse(database.isReserved(3, 7));
        database.addReservation(3, 7, 9);
        assertTrue(database.isReserved(3, 7));
    }

    @Test
    public void updateGapRules() {
        assertFalse(database.isGapRule(4));
        database.addGapRule(4);
        assertTrue(database.isGapRule(4));
        database.removeGapRule(3);
        assertFalse(database.isGapRule(3));
        assertEquals(4, database.getMaxGapRule());
    }
}

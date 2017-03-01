import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Test the methods of the <code>CampsiteDatabase</code> class.
 */
public class DatabaseTest {
    CampsiteDatabase database;
    CampsiteQuery query;

    /**
     * Build the <code>CampsiteDatabase</code> and <code>CampsiteQuery</code> objects to use for testing.
     * @throws ParseException
     * @throws IOException
     */
    public DatabaseTest() throws ParseException, IOException {
        CampsiteDataParser parser = new CampsiteDataParser(new File("src/main/resources/test-case.json"));
        database = parser.buildSearchDatabase();
        query = parser.buildSearchQuery();
    }

    /**
     * Confirm the database was built correctly.
     */
    @Test
    public void confirmBaseTestCase() {
        assertTrue(database.isReserved(4, 156));
        assertFalse(database.isReserved(6, 165));
    }

    /**
     * Add and remove campsites.
     */
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

    /**
     * Add reservations.
     */
    @Test
    public void updateReservations() {
        assertFalse(database.isReserved(3, 123));
        database.addReservation(3, 123, 126);
        assertTrue(database.isReserved(3, 125));
    }

    /**
     * Add and remove gap sizes from to the set of gap rules.
     */
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

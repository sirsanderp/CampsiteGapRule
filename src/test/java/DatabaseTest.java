import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sander Peerna on 2/28/2017.
 */
public class UnitTest {
    SearchDatabase database;
    SearchQuery query;

    public UnitTest() throws ParseException {
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
    public void updateReservations
}

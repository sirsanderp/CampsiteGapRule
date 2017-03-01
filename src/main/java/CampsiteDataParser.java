import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Class for building <code>CampsiteDatabase</code> and <code>CampsiteQuery</code> objects from the JSON input.
 *
 * @author  Sander Peerna
 * @version 0.1
 * @since   2/26/2017
 */
public class CampsiteDataParser {
    private ObjectMapper mapper;
    private JsonNode rootNode;

    /**
     * Class Constructor.
     * @param file  the JSON file to parse
     * @throws IOException
     */
    public CampsiteDataParser(File file) throws IOException{
        mapper = new ObjectMapper();
        rootNode = mapper.readTree(file);
    }

    /**
     * Builds a <code>CampsiteDatabase</code> object based on the JSON input.
     * @return the <code>CampsiteDatabase</code> object built from parsing the JSON
     * @throws ParseException
     */
    public CampsiteDatabase buildSearchDatabase() throws ParseException {
        CampsiteDatabase db = new CampsiteDatabase();
        JsonNode campsites = rootNode.path("campsites");
        Iterator<JsonNode> iterator = campsites.elements();
        while (iterator.hasNext()) {                        // Build the campsite map.
            JsonNode campsite = iterator.next();

            long id = campsite.path("id").asLong();
            String name = campsite.path("name").asText();
            db.addCampsite(id, name);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(rootNode.path("search").path("startDate").asText()));
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR); // Check for leap year.

        JsonNode reservations = rootNode.path("reservations");
        iterator = reservations.elements();
        while (iterator.hasNext()) {                        // Build the yearly reservation calendar for the campsite.
            JsonNode reservation = iterator.next();

            long id = reservation.path("campsiteId").asLong();
            calendar.setTime(format.parse(reservation.path("startDate").asText()));
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(format.parse(reservation.path("endDate").asText()));
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
            db.addReservation(id, startDay, endDay);

            // If not a leap year then marked the leap day as "reserved".
            if (daysInYear != 366) db.addReservation(id, 366, 366);
        }

        JsonNode gapRules = rootNode.path("gapRules");
        iterator = gapRules.elements();
        while (iterator.hasNext()) {                        // Build the gap rule set.
            JsonNode gapRule = iterator.next();

            int gapSize = gapRule.path("gapSize").asInt();
            db.addGapRule(gapSize);
        }
        return db;
    }

    /**
     * Builds a <code>CampsiteQuery</code> object based on the JSON input.
     * @return the <code>CampsiteQuery</code> object built from parsing the JSON
     * @throws ParseException
     */
    public CampsiteQuery buildSearchQuery() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        JsonNode search = rootNode.path("search");
        calendar.setTime(format.parse(search.path("startDate").asText()));
        int startDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(format.parse(search.path("endDate").asText()));
        int endDay = calendar.get(Calendar.DAY_OF_YEAR);

        return new CampsiteQuery(startDay, endDay);
    }
}

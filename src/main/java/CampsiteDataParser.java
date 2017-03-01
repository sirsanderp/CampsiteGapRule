import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sander Peerna on 2/26/2017.
 */

/**
 * Class for building <code>SearchDatabase</code> and <code>SearchQuery</code> objects from the JSON input.
 *
 * @author  Sander Peerna
 * @version 0.1
 * @since   2/26/2017
 */
public class SearchDataParser {
//    private static final Logger LOGGER = Logger.getLogger(SearchDataParser.class.getName());

    private ObjectMapper mapper;
    private JsonNode rootNode;

    /**
     * Class Constructor.
     * @param file  the JSON file to parse
     * @exception IOException
     */
    public SearchDataParser(File file) throws IOException{
        mapper = new ObjectMapper();
        rootNode = mapper.readTree(file);
    }

    /**
     * 
     * @return
     * @throws ParseException
     */
    public SearchDatabase buildSearchDatabase() throws ParseException {
        SearchDatabase db = new SearchDatabase();
        JsonNode campsites = rootNode.path("campsites");
        Iterator<JsonNode> iterator = campsites.elements();
        while (iterator.hasNext()) {
            JsonNode campsite = iterator.next();

            long id = campsite.path("id").asLong();
            String name = campsite.path("name").asText();
//            System.out.println(id + " " + name);
            db.addCampsite(id, name);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(rootNode.path("search").path("startDate").asText()));
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        JsonNode reservations = rootNode.path("reservations");
        iterator = reservations.elements();
        while (iterator.hasNext()) {
            JsonNode reservation = iterator.next();

            long id = reservation.path("campsiteId").asLong();
//            System.out.println(reservation.path("startDate").asText() + " - " + reservation.path("endDate").asText());
            calendar.setTime(format.parse(reservation.path("startDate").asText()));
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(format.parse(reservation.path("endDate").asText()));
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
//            System.out.println(id + ": " + startDay + " - " + endDay);
            db.addReservation(id, startDay, endDay);

            if (daysInYear != 366) db.addReservation(id, 366, 366);
        }

        JsonNode gapRules = rootNode.path("gapRules");
        iterator = gapRules.elements();
        while (iterator.hasNext()) {
            JsonNode gapRule = iterator.next();

            int gapSize = gapRule.path("gapSize").asInt();
//            System.out.println(gapSize);
            db.addGapRule(gapSize);
        }
        return db;
    }

    public SearchQuery buildSearchQuery() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        JsonNode search = rootNode.path("search");
        calendar.setTime(format.parse(search.path("startDate").asText()));
        int startDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(format.parse(search.path("endDate").asText()));
        int endDay = calendar.get(Calendar.DAY_OF_YEAR);
//        System.out.println(startDay + " " + endDay);

        return new SearchQuery(startDay, endDay);
    }
}

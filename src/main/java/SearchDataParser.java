import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sander Peerna on 2/26/2017.
 */
public class SearchDataParser {
//    private static final Logger LOGGER = Logger.getLogger(SearchDataParser.class.getName());

    private ObjectMapper mapper;
    private JsonNode rootNode;

    public SearchDataParser(File file) {
        try {
            mapper = new ObjectMapper();
            rootNode = mapper.readTree(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearchDatabase buildSearchDatabase() throws ParseException {
        SearchDatabase db = new SearchDatabase();
        JsonNode campsites = rootNode.path("campsites");
        Iterator<JsonNode> iterator = campsites.elements();
        while (iterator.hasNext()) {
            JsonNode campsite = iterator.next();

            long id = campsite.path("id").asLong();
            String name = campsite.path("name").asText();
//            LOGGER.log(Level.INFO, id + " " + name);
//            System.out.println(id + " " + name);
            db.addCampsite(id, name);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        JsonNode reservations = rootNode.path("reservations");
        iterator = reservations.elements();
        while (iterator.hasNext()) {
            JsonNode reservation = iterator.next();

            long id = reservation.path("campsiteId").asLong();
//            System.out.println(reservation.path("startDate").asText() + " - " + reservation.path("endDate").asText());
            Date startDate = format.parse(reservation.path("startDate").asText());
            Date endDate = format.parse(reservation.path("endDate").asText());

            calendar.setTime(startDate);
            int startDay = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_MONTH);
//            System.out.println(id + ": " + startDay + " - " + endDay);
            db.addReservation(id, startDay, endDay);
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
        Date startDate = format.parse(search.path("startDate").asText());
        Date endDate = format.parse(search.path("endDate").asText());

        calendar.setTime(startDate);
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(endDate);
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
//        System.out.println(startDay + " " + endDay);

        return new SearchQuery(startDay, endDay);
    }
}

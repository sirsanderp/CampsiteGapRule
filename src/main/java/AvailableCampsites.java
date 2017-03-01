import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Class to find the available campsites.
 *
 * @author  Sander Peerna
 * @version 0.1
 * @since   2/24/2017
 */
public class AvailableCampsites {

    /**
     * Builds all the objects required to find available campsites based on the JSON input.
     * @param args the location of the JSON file
     */
    public static void main (String[] args) {
        CampsiteDataParser parser = null;
        try {
            if (args.length == 0) parser = new CampsiteDataParser(new File("src/main/resources/test-case.json"));
            else parser = new CampsiteDataParser(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            CampsiteDatabase database = parser.buildSearchDatabase();
            CampsiteQuery query = parser.buildSearchQuery();
            ArrayList<String> result = query.findAvailableCampsites(database);

            for (String campsite : result) {
                System.out.println(campsite);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

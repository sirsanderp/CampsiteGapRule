import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing a query for finding available campsites reservations.
 *
 * @author  Sander Peerna
 * @version 0.1
 * @since   2/24/2017
 */
public class CampsiteQuery {
    private int startDay;
    private int endDay;

    /**
     * Class constructor.
     * Assume the days are checked for validity by a frontend that provided the JSON data.
     * @param startDay  the reservation start day of the year (1-366)
     * @param endDay    the reservation end day of the year (1-366)
     */
    public CampsiteQuery(int startDay, int endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    /**
     * Find the campsites that are available for reservation given the database and gap rules.
     * @param database  the reservation database
     * @return          a list of available campsites
     */
    public ArrayList<String> findAvailableCampsites(CampsiteDatabase database) {
        ArrayList<String> availableCampsites = new ArrayList<>();
        HashMap<Long, String> campsites = database.getCampsites();
        for (long id : campsites.keySet()) {
            // Check if any of the requested reservation days are already reserved.
            boolean overlap = false;
            for (int day = startDay; day <= endDay; day++) {
                if (database.isReserved(id, day)) {
                    overlap = true;
                    break;
                }
            }
            if (overlap) continue;

            // Check if the gap before the start day of the requested reservation is in the gap rule.
            int maxGap = database.getMaxGapRule();
            int beforeGap = 0;
            while (beforeGap <= maxGap + 1) {
                int day = startDay - 1 - beforeGap;
                if (day >= 1 && !database.isReserved(id, day)) {
                    beforeGap++;
                } else {
                    break;
                }
            }
            if (database.isGapRule(beforeGap)) continue;

            // Check if the gap after the end day of the requested reservation is in the gap rule.
            int afterGap = 0;
            while (afterGap <= maxGap + 1) {
                int day = endDay + 1 + afterGap;
                if (day <= 366 && !database.isReserved(id, day)) {
                    afterGap++;
                } else {
                    break;
                }
            }
            if (database.isGapRule(afterGap)) continue;

            availableCampsites.add(database.getCampsite(id));
        }
        return availableCampsites;
    }

    /**
     * Change the reservation start day and end day for the query.
     * @param startDay  the reservation start day of the year (1-366)
     * @param endDay    the reservation end day of the year (1-366)
     */
    public void updateQuery(int startDay, int endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }
}

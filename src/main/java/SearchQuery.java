import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sander Peerna on 2/24/2017.
 */
public class SearchQuery {
    private int startDay;
    private int endDay;

    public SearchQuery(int startDay, int endDay) {
        if (startDay < 1 || endDay > 31 || startDay > endDay) {
            System.out.println("Invalid days");
            return;
        }
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public ArrayList<String> findAvailableCampsites(SearchDatabase database) {
        ArrayList<String> availableCampsites = new ArrayList<>();
        HashMap<Long, String> campsites = database.getCampsites();
        for (long id : campsites.keySet()) {
            boolean overlap = false;
            for (int day = startDay; day <= endDay; day++) {
                if (database.isReserved(id, day)) {
                    overlap = true;
                    break;
                }
            }
            if (overlap) continue;

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

            int afterGap = 0;
            while (afterGap <= maxGap + 1) {
                int day = endDay + 1 + afterGap;
                if (day <= 31 && !database.isReserved(id, day)) {
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

    public void updateQuery(int startDay, int endDay) {
        if (startDay < 1 || endDay > 31 || startDay > endDay) {
            System.out.println("Invalid days");
            return;
        }
        this.startDay = startDay;
        this.endDay = endDay;
    }
}

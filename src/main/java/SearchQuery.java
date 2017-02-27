import java.util.HashMap;

/**
 * Created by Sander Peerna on 2/24/2017.
 */
public class SearchQuery {
    private int startDay;
    private int endDay;

    public SearchQuery(int startDay, int endDay) {
        if (startDay > endDay) System.out.println("Invalid days");
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public void findAvailableCampsites(SearchDatabase database) {
        HashMap<Long, boolean[]> reservations = database.getReservation();
        for (long id : reservations.keySet()) {
            boolean[] month = reservations.get(id);
            boolean overlap = false;
            for (int day = startDay; day <= endDay; day++) {
                if (month[day]) {
                    overlap = true;
                    break;
                }
            }
            if (overlap) continue;

            int maxGap = database.getMaxGapRule();
            int beforeGap = 0;
            while (beforeGap <= maxGap + 1) {
                if (!month[startDay - 1 - beforeGap]) {
                    beforeGap++;
                } else {
                    break;
                }
            }
            if (database.isGapRule(beforeGap)) continue;

            int afterGap = 0;
            while (afterGap <= maxGap + 1) {
                if (!month[endDay + 1 + afterGap]) {
                    afterGap++;
                } else {
                    break;
                }
            }
            if (database.isGapRule(afterGap)) continue;

            System.out.println(database.getCampsite(id));
        }
    }

    public void updateQuery(int startDay, int endDay) {
        if (startDay > endDay) System.out.println("Invalid days");
        this.startDay = startDay;
        this.endDay = endDay;
    }
}

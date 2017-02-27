import java.util.*;

/**
 * Created by Sander Peerna on 2/24/2017.
 */
public class SearchDatabase {
    private static final int DAYS = 32;

    private HashMap<Long, String> campsites;        // Assume id's are not 1 -> # of campsites, could be 43299292, ..., 50230203, ...
    private HashMap<Long, boolean[]> reservations;
    private ArrayList<Integer> gapRules;        // Assume there are only a few rules

    public SearchDatabase() {
        campsites = new HashMap<>();
        reservations = new HashMap<>();
        gapRules = new ArrayList<>();
    }

    public void addCampsite(long id, String name) {
        campsites.putIfAbsent(id, name);
    }

    public String getCampsite(long id) {
        return campsites.get(id);
    }

    // Assume input is for one calendar month, otherwise have to make an array for 365 days or use a real database...
    public void addReservation(long id, int startDay, int endDay) {
        boolean[] month;
        if (reservations.get(id) == null) {
            month = new boolean[DAYS];
        } else {
            month = reservations.get(id);
        }

        for (int day = startDay; day <= endDay; day++) {
            month[day] = true;
        }

        reservations.put(id, month);
    }

    public HashMap<Long, boolean[]> getReservation() {
        return reservations;
    }

    public boolean isReserved(long id, int day) {
        return reservations.get(id)[day];
    }

    public void addGapRule(int gap) {
        gapRules.add(gap);
    }

    public boolean removeGapRule(int gap) {
        return gapRules.remove(gap) > 0;
    }

    public int getMaxGapRule() {
        Collections.sort(gapRules);
        return gapRules.get(gapRules.size() - 1);
    }

    public ArrayList<Integer> getGapRules() {
        return gapRules;
    }

    public boolean isGapRule(int gap) {
        return gapRules.contains(gap);
    }
}
